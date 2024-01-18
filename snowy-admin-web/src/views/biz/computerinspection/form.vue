<template>
	<xn-form-container :title="formData.pkId ? '编辑机房巡检' : '增加机房巡检'" :width="700" :visible="visible" :destroy-on-close="true"
		@close="onClose">
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="巡检项目名称：" name="temp">
				<a-select showSearch v-model:value="formData.temp" placeholder="请选择项目" optionFilterProp="label"
					:options="loginUserSelectList" :field-names="{ label: 'nameName', value: 'pkId' }"
					@change="selectLoginUserList" />
			</a-form-item>
			<a-form-item label="项目名称：" name="inspectionName">
				<a-select disabled showSearch v-model:value="formData.inspectionName" placeholder="请选择项目" optionFilterProp="label"
					:options="projectList" />
			</a-form-item>
			<a-form-item name="inspectionUsers">
				<a-button disabled type="primary" @click="openUserSelector">巡检人员</a-button>
				<br />
				<a-tag v-for="(user, index) in formData.userList" color="cyan" :key="index">{{
					user.name
				}}</a-tag>
			</a-form-item>
			<!-- <a-form-item label="经度：" name="longitude">
				<a-input v-model:value="formData.longitude" placeholder="请输入经度" allow-clear />
			</a-form-item>
			<a-form-item label="纬度：" name="latitude">
				<a-input v-model:value="formData.latitude" placeholder="请输入纬度" allow-clear />
			</a-form-item> -->
			<a-form-item label="巡检项目：" name="inspectionType">
				<a-select disabled @change="selectTypeListOn" showSearch v-model:value="formData.inspectionType" placeholder="请选择项目"
					optionFilterProp="label" :options="typeList" />
			</a-form-item>
			<a-form-item label="" name="inspectionDetail">
				{{ "巡检报告：" }}
				<a-form :inline="true" v-for="(_, index) in inspectionDetailArray" :key="index">
					<!-- <a-input :addon-before="inspectionDetailArray[index].k" v-model:value="inspectionDetailArray[index].v"
						placeholder="" allow-clear style="margin-top: 8px" /> -->
					<a-button style="margin-top: 8px; margin-right: 3px; pointer-events: none;">
						{{ inspectionDetailArray[index].k }}
					</a-button>
					<a-radio-group v-model:value="inspectionDetailArray[index].flag">
						<a-radio-button :value="'ok'">正常</a-radio-button>
						<a-radio-button :value="'error'">异常</a-radio-button>
						<a-radio-button :value="'text'">
							其他
							<a-input :bordered="false" placeholder="请输入报告内容" v-model:value="inspectionDetailArray[index].v"
								v-if="inspectionDetailArray[index].flag === 'text'"
								style="width: 200px; margin-left: -5px; margin-right: -15px" />
						</a-radio-button>
					</a-radio-group>
				</a-form>
			</a-form-item>
			<!-- <a-form-item label="作业计划：" name="workPlan">
				<a-input v-model:value="formData.workPlan" placeholder="请输入作业计划" allow-clear />
			</a-form-item> -->
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
	<user-selector-plus ref="userSelectorPlusRef" :org-tree-api="selectorApiFunction.orgTreeApi"
		:user-page-api="selectorApiFunction.userPageApi" :checked-user-list-api="selectorApiFunction.checkedUserListApi"
		@onBack="userBack" />
</template>

<script setup name="tComputerInspectionForm">
import { cloneDeep } from 'lodash-es'
import tComputerInspectionApi from '@/api/biz/tComputerInspectionApi'
import tComputerInspectionTypeApi from '@/api/biz/tComputerInspectionTypeApi'
import tComputerInspectionManagementApi from '@/api/biz/tComputerInspectionManagementApi'
import tProjectApi from '@/api/biz/tProjectApi'
import { required } from '@/utils/formRules'
import userApi from '@/api/sys/userApi'
import userCenterApi from '@/api/sys/userCenterApi'
import { message } from 'ant-design-vue'
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })
const formRef = ref()
// 表单数据
const formData = ref({})
const submitLoading = ref(false)
const projectList = ref([])
const userSelectorPlusRef = ref()
const loginUserSelectList = ref([])

// 打开抽屉
const onOpen = (record) => {
	visible.value = true
	if (record) {
		let recordData = cloneDeep(record)
		formData.value = Object.assign({}, recordData)
		for (let i = 0; i < formData.value.remarkReport.length; i++) {
			inspectionDetailArray.value.push({
				k: formData.value.remarkReport[i].k,
				v: formData.value.remarkReport[i].v,
				flag: valueByFlag(formData.value.remarkReport[i].v)
			})
		}
	}
	listByLoginUser()
	selectProjectList()
	selectTypeList()
}
// 关闭抽屉
const onClose = () => {
	formRef.value.resetFields()
	formData.value = {}
	visible.value = false
	inspectionDetailArray.value = []
}
// 默认要校验的
const formRules = {
	inspectionName: [required('请选择项目名称')],
}
// 验证并提交数据
const onSubmit = () => {
	formRef.value.validate().then(() => {
		submitLoading.value = true
		let array = []
		for (let i = 0; i < inspectionDetailArray.value.length; i++) {
			if (inspectionDetailArray.value[i] && inspectionDetailArray.value[i] != "") {
				inspectionDetailArray.value[i].v = flagByValue(inspectionDetailArray.value[i])
				delete inspectionDetailArray.value[i].flag
				array.push(inspectionDetailArray.value[i])
			}
		}
		if (formData.value.userList.length < 1) {
			message.warning('未选择巡检人员')
			return
		}
		formData.value.remarkReport = JSON.stringify(array)
		convFormData()
		const formDataParam = cloneDeep(formData.value)
		formDataParam.inspectionUsers = JSON.stringify(formDataParam.inspectionUsers)
		tComputerInspectionApi
			.tComputerInspectionSubmitForm(formDataParam, formDataParam.pkId)
			.then(() => {
				onClose()
				emit('successful')
			})
			.finally(() => {
				submitLoading.value = false
			})
	})
}
const selectProjectList = () => {
	projectList.value = [];
	tProjectApi.projectList().then(res => {
		res.forEach(i => {
			const newI = {
				value: i.pkId,
				label: i.projectName,
			};
			projectList.value.push(newI)
		})
	})
}
// 获取巡检管理
const listByLoginUser = () => {
	loginUserSelectList.value = [];
	tComputerInspectionManagementApi.listByLoginUser().then(res => {
		loginUserSelectList.value = res;
	})
}

// 打开人员选择器
const openUserSelector = () => {
	userSelectorPlusRef.value.showUserPlusModal(formData.value.inspectionUsers)
}
// 人员选择回调
const userBack = (value) => {
	formData.value.userList = value
}
// 添加接收人
const convFormData = () => {
	let ids = []
	formData.value.userList.forEach((item) => {
		ids.push(item.id)
	})
	formData.value.inspectionUsers = ids
}
// 传递设计器需要的API
const selectorApiFunction = {
	orgTreeApi: (param) => {
		return userApi.userOrgTreeSelector(param).then((data) => {
			return Promise.resolve(data)
		})
	},
	userPageApi: (param) => {
		return userApi.userSelector(param).then((data) => {
			return Promise.resolve(data)
		})
	},
	checkedUserListApi: (param) => {
		return userCenterApi.userCenterGetUserListByIdList(param).then((data) => {
			return Promise.resolve(data)
		})
	}
}
const typeList = ref([])
const selectTypeList = () => {
	typeList.value = [];
	tComputerInspectionTypeApi.list().then(res => {
		res.forEach(i => {
			const newI = {
				value: i.pkId,
				label: i.inspectionTypeName,
				list: i.inspectionDetail,
			};
			typeList.value.push(newI)
		})
	})
}
const inspectionDetailArray = ref([])
const selectTypeListOn = (value) => {
	inspectionDetailArray.value = []
	let list = typeList.value.filter(item => item.value == value)[0].list
	for (let i = 0; i < list.length; i++) {
		inspectionDetailArray.value.push({
			k: list[i],
			v: ""
		})
	}
}
const selectLoginUserList = (value) => {
	let selectValue = loginUserSelectList.value.filter(item => item.pkId == value)[0]
	formData.value = selectValue
	console.log(selectValue)
}

function flagByValue(value) {
	if (value.flag === 'ok') {
		return '正常'
	} else if (value.flag === 'error') {
		return '异常'
	} else {
		return value.v
	}
}
function valueByFlag(value) {
	if (value === '正常') {
		return "ok"
	} else if (value === '异常') {
		return 'error'
	} else {
		return 'text'
	}
}
// 抛出函数
defineExpose({
	onOpen
})
</script>
