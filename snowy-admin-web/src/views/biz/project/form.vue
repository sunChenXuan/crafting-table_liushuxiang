<template>
	<xn-form-container :title="formData.pkId ? '编辑项目' : '增加项目'" :width="700" :visible="visible" :destroy-on-close="true"
		@close="onClose">
			<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">

			<a-form-item label="所属公司：" name="projectCompany">
				<a-select v-model:value="formData.projectCompany" placeholder="请选择所属公司" :options="projectCompanyTypeOptions" />
			</a-form-item>
			<a-form-item label="项目名称：" name="projectName">
				<a-input v-model:value="formData.projectName" placeholder="请输入项目名称" allow-clear />
			</a-form-item>
			<a-form-item label="项目描述：" name="projectDescription">
				<a-input v-model:value="formData.projectDescription" placeholder="请输入项目描述" allow-clear />
			</a-form-item>
			<a-form-item label="联系人：" name="projectContacts">
				<a-input v-model:value="formData.projectContacts" placeholder="请输入联系人" allow-clear />
			</a-form-item>
			<a-form-item label="联系电话：" name="projectPhone">
				<a-input v-model:value="formData.projectPhone" placeholder="请输入联系电话" allow-clear />
			</a-form-item>
			<a-form-item label="负责人：" name="projectHeadUsers">
				<a-button type="primary" @click="openHeadUserSelector">选择人员</a-button>
				<br />
				<a-tag v-for="(user, index) in formData.projectHeadUserList" color="cyan" :key="index">{{
					user.name
				}}</a-tag>
			</a-form-item>
			<!-- <a-form-item label="职工：" name="projectUsers">
				<a-button type="primary" @click="openUserSelector">选择职工</a-button>
				<br />
				<a-tag v-for="(user, index) in formData.projectUserList" color="cyan" :key="index">{{
					user.name
				}}</a-tag>
			</a-form-item> -->
			<a-form-item label="项目开始时间：" name="projectStartTime">
				<a-date-picker v-model:value="formData.projectStartTime" value-format="YYYY-MM-DD HH:mm:ss" show-time
					placeholder="请选择项目开始时间" style="width: 100%" />
			</a-form-item>
			<a-form-item label="项目结束时间：" name="projectEndTime">
				<a-date-picker v-model:value="formData.projectEndTime" value-format="YYYY-MM-DD HH:mm:ss" show-time
					placeholder="请选择项目结束时间" style="width: 100%" />
			</a-form-item>
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

<script setup name="tProjectForm">
import { cloneDeep } from 'lodash-es'
import { required, rules } from '@/utils/formRules'
import tProjectApi from '@/api/biz/tProjectApi'
import userApi from '@/api/sys/userApi'
import userCenterApi from '@/api/sys/userCenterApi'
import { message } from 'ant-design-vue'
import tool from "@/utils/tool";
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })
const formRef = ref()
// 表单数据
const formData = ref({})
const submitLoading = ref(false)
// 这个没想明白
const userSelectorPlusRef = ref()
const ifHead = ref(false)
const projectCompanyTypeOptions = ref([])

// 打开抽屉
const onOpen = (record) => {
	visible.value = true
	if (record) {
		let recordData = cloneDeep(record)
		formData.value = Object.assign({}, recordData)
	}
	projectCompanyTypeOptions.value = tool.dictList('PROJECT_COMPANY')
}
// 关闭抽屉
const onClose = () => {
	formRef.value.resetFields()
	formData.value = {}
	visible.value = false
}
// 默认要校验的
const formRules = {
	projectName: [required('请输入项目名称')],
	projectDescription: [required('请输入项目描述')],
	projectContacts: [required('请输入联系人')],
	projectPhone: [
		required('请输入联系电话'), rules.phone
	],
}


// 打开人员选择器Head
const openHeadUserSelector = () => {
	ifHead.value = true
	userSelectorPlusRef.value.showUserPlusModal(formData.value.projectHeadUsers)
}
// 打开人员选择器
// const openUserSelector = () => {
// 	ifHead.value = false
// 	userSelectorPlusRef.value.showUserPlusModal(formData.value.projectUsers)
// }
// 人员选择回调
const userBack = (value) => {
	if (ifHead.value) {
		formData.value.projectHeadUserList = value
	} else {
		// formData.value.projectUserList = value
	}
}
// 添加接收人
const convFormData = () => {
	let headIds = []
	formData.value.projectHeadUserList.forEach((item) => {
		headIds.push(item.id)
	})
	formData.value.projectHeadUsers = headIds
	// let ids = []
	// formData.value.projectUserList.forEach((item) => {
	// 	ids.push(item.id)
	// })
	// formData.value.projectUsers = ids
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

// 验证并提交数据
const onSubmit = () => {
	formRef.value.validate().then(() => {
		if (!formData.value.projectHeadUserList || formData.value.projectHeadUserList.length < 1) {
			message.warning('未选择负责人')
			return
		}
		// if (formData.value.projectUserList.length < 1) {
		// 	message.warning('未选职工')
		// 	return
		// }
		convFormData()
		submitLoading.value = true
		const formDataParam = cloneDeep(formData.value)
		formDataParam.projectHeadUsers = JSON.stringify(formDataParam.projectHeadUsers)
		formDataParam.projectUsers = null
		tProjectApi
			.tProjectSubmitForm(formDataParam, formDataParam.pkId)
			.then(() => {
				onClose()
				emit('successful')
			})
			.finally(() => {
				submitLoading.value = false
			})
	})
}
// 抛出函数
defineExpose({
	onOpen
})
</script>
