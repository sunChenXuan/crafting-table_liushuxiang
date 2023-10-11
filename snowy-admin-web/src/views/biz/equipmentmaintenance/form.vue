<template>
	<xn-form-container :title="formData.pkId ? '编辑设备维保' : '增加设备维保'" :width="700" :visible="visible" :destroy-on-close="true"
		@close="onClose">
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="项目名称：" name="projectName">
				<a-input v-if="formData.projectName" disabled v-model:value="formData.projectName" placeholder="请在项目管理选择项目"
					allow-clear />
				<a-input v-else="projectName" disabled v-model:value="projectName" placeholder="请在项目管理选择项目" allow-clear />
			</a-form-item>
			<a-form-item label="设备名称：" name="equipmentName">
				<a-input :disabled="onOpenTimeBool" v-model:value="formData.equipmentName" placeholder="请输入设备名称"
					allow-clear />
			</a-form-item>
			<a-form-item label="设备类型：" name="equipmentType">
				<a-select :disabled="onOpenTimeBool" v-model:value="formData.equipmentType" placeholder="请选择设备类型"
					:options="equipmentTypeOptions" />
			</a-form-item>
			<a-form-item label="设备厂家：" name="equipmentManufacturer">
				<a-select :disabled="onOpenTimeBool" v-model:value="formData.equipmentManufacturer" placeholder="请选择设备厂家"
					:options="equipmentManufacturerOptions" />
			</a-form-item>
			<a-form-item label="序列号：" name="serialNumber">
				<a-input :disabled="onOpenTimeBool" v-model:value="formData.serialNumber" placeholder="请输入序列号"
					allow-clear />
			</a-form-item>
			<a-form-item label="授权开始时间：" name="authorizationStartTime">
				<a-date-picker :disabled="formData.pkId" v-model:value="formData.authorizationStartTime"
					value-format="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择授权开始时间" style="width: 100%" />
			</a-form-item>
			<a-form-item label="授权结束时间：" name="authorizationEndTime">
				<a-date-picker :disabled="!onOpenTimeBool && formData.pkId" v-model:value="formData.authorizationEndTime"
					value-format="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择授权结束时间" style="width: 100%" />
			</a-form-item>
			<a-form-item v-if="!onOpenTimeBool" label="设备提醒人：" name="equipmentUsers">
				<a-button type="primary" @click="openUserSelector">设备提醒人</a-button>
				<br />
				<a-tag v-for="(user, index) in formData.equipmentUserList" color="cyan" :key="index">{{
					user.name
				}}</a-tag>
			</a-form-item>
			<a-form-item v-if="!formData.pkId" label="上传图片" name="authorizationEndTime">
				{{图}}
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

<script setup name="tEquipmentMaintenanceForm">
import tool from '@/utils/tool'
import { cloneDeep } from 'lodash-es'
import tEquipmentMaintenanceApi from '@/api/biz/tEquipmentMaintenanceApi'
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
const equipmentTypeOptions = ref([])
const equipmentManufacturerOptions = ref([])
const projectName = ref([])
const onOpenTimeBool = ref(false)
// 回显需要
const userSelectorPlusRef = ref()
const ifSys = ref(false)

// 打开抽屉
const onOpenTime = (routePkId, routeProjectName, record) => {
	onOpenTimeBool.value = true
	onOpen(routePkId, routeProjectName, record)
}
const onOpen = (routePkId, routeProjectName, record) => {
	visible.value = true
	projectName.value = routeProjectName
	if (record) {
		let recordData = cloneDeep(record)
		formData.value = Object.assign({}, recordData)
	}
	formData.value.idxProjectId = routePkId
	equipmentTypeOptions.value = tool.dictList('EQUIPMENT_TYPE')
	equipmentManufacturerOptions.value = tool.dictList('EQUIPMENT_MANUFACTURER')
}


// 打开人员选择器Head
const openSysUserSelector = () => {
	ifSys.value = true
	userSelectorPlusRef.value.showUserPlusModal(formData.value.equipmentSysUsers)
}
// 打开人员选择器
const openUserSelector = () => {
	ifSys.value = false
	userSelectorPlusRef.value.showUserPlusModal(formData.value.equipmentUsers)
}
// 人员选择回调
const userBack = (value) => {
	if (ifSys.value) {
		formData.value.equipmentSysUserList = value
	} else {
		formData.value.equipmentUserList = value
	}
}
// 添加接收人
const convFormData = () => {
	let headIds = []
	formData.value.equipmentSysUsers = headIds
	let ids = []
	if (formData.value.equipmentUserList && formData.value.equipmentUserList.length > 0) {
		formData.value.equipmentUserList.forEach((item) => {
			ids.push(item.id)
		})
	}
	formData.value.equipmentUsers = ids
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

// 关闭抽屉
const onClose = () => {
	formRef.value.resetFields()
	formData.value = {}
	visible.value = false
	onOpenTimeBool.value = false
}
// 默认要校验的
const formRules = {
}
// 验证并提交数据
const onSubmit = () => {
	formRef.value.validate().then(() => {
		submitLoading.value = true
		// if (formData.value.equipmentUserList.length < 1) {
		// 	message.warning('未选择负责人')
		// 	return
		// }
		if (!formData.value.equipmentUserList || formData.value.equipmentUserList.length < 1) {
			// message.warning('未选职工')
			// return
		}
		convFormData()
		const formDataParam = cloneDeep(formData.value)
		// formDataParam.equipmentSysUsers = JSON.stringify(formDataParam.equipmentSysUsers)
		formDataParam.equipmentUsers = JSON.stringify(formDataParam.equipmentUsers)
		tEquipmentMaintenanceApi
			.tEquipmentMaintenanceSubmitForm(formDataParam, formDataParam.pkId)
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
	onOpen,
	onOpenTime
})
</script>
