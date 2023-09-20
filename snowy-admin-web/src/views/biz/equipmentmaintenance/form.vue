<template>
	<xn-form-container
		:title="formData.pkId ? '编辑设备维保' : '增加设备维保'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="项目id：" name="idxProjectId">
				<a-input v-model:value="formData.idxProjectId" placeholder="请输入项目id" allow-clear />
			</a-form-item>
			<a-form-item label="设备名称：" name="equipmentName">
				<a-input v-model:value="formData.equipmentName" placeholder="请输入设备名称" allow-clear />
			</a-form-item>
			<a-form-item label="设备类型：" name="equipmentType">
				<a-select v-model:value="formData.equipmentType" placeholder="请选择设备类型" :options="equipmentTypeOptions" />
			</a-form-item>
			<a-form-item label="设备厂家：" name="equipmentManufacturer">
				<a-select v-model:value="formData.equipmentManufacturer" placeholder="请选择设备厂家" :options="equipmentManufacturerOptions" />
			</a-form-item>
			<a-form-item label="序列号：" name="serialNumber">
				<a-input v-model:value="formData.serialNumber" placeholder="请输入序列号" allow-clear />
			</a-form-item>
			<a-form-item label="授权开始时间：" name="authorizationStartTime">
				<a-date-picker v-model:value="formData.authorizationStartTime" value-format="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择授权开始时间" style="width: 100%" />
			</a-form-item>
			<a-form-item label="授权结束时间：" name="authorizationEndTime">
				<a-date-picker v-model:value="formData.authorizationEndTime" value-format="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择授权结束时间" style="width: 100%" />
			</a-form-item>
			<a-form-item label="系统设备提醒人：" name="equipmentSysUsers">
				<a-checkbox-group v-model:value="formData.equipmentSysUsers" placeholder="请选择系统设备提醒人" :options="equipmentSysUsersOptions" />
			</a-form-item>
			<a-form-item label="设备提醒人：" name="equipmentUsers">
				<a-checkbox-group v-model:value="formData.equipmentUsers" placeholder="请选择设备提醒人" :options="equipmentUsersOptions" />
			</a-form-item>
			<a-form-item label="创建用户：" name="createdBy">
				<a-input v-model:value="formData.createdBy" placeholder="请输入创建用户" allow-clear />
			</a-form-item>
			<a-form-item label="修改时间：" name="updatedTime">
				<a-date-picker v-model:value="formData.updatedTime" value-format="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择修改时间" style="width: 100%" />
			</a-form-item>
			<a-form-item label="修改用户：" name="updatedBy">
				<a-input v-model:value="formData.updatedBy" placeholder="请输入修改用户" allow-clear />
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="tEquipmentMaintenanceForm">
	import tool from '@/utils/tool'
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import tEquipmentMaintenanceApi from '@/api/biz/tEquipmentMaintenanceApi'
	// 抽屉状态
	const visible = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	// 表单数据
	const formData = ref({})
	const submitLoading = ref(false)
	const equipmentTypeOptions = ref([])
	const equipmentManufacturerOptions = ref([])
	const equipmentSysUsersOptions = ref([])
	const equipmentUsersOptions = ref([])

	// 打开抽屉
	const onOpen = (record) => {
		visible.value = true
		if (record) {
			let recordData = cloneDeep(record)
			recordData.equipmentSysUsers = JSON.parse(recordData.equipmentSysUsers)
			recordData.equipmentUsers = JSON.parse(recordData.equipmentUsers)
			formData.value = Object.assign({}, recordData)
		}
		equipmentTypeOptions.value = tool.dictList('GENDER')
		equipmentManufacturerOptions.value = tool.dictList('GENDER')
		equipmentSysUsersOptions.value = tool.dictList('GENDER')
		equipmentUsersOptions.value = tool.dictList('GENDER')
	}
	// 关闭抽屉
	const onClose = () => {
		formRef.value.resetFields()
		formData.value = {}
		visible.value = false
	}
	// 默认要校验的
	const formRules = {
	}
	// 验证并提交数据
	const onSubmit = () => {
		formRef.value.validate().then(() => {
			submitLoading.value = true
			const formDataParam = cloneDeep(formData.value)
			formDataParam.equipmentSysUsers = JSON.stringify(formDataParam.equipmentSysUsers)
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
		onOpen
	})
</script>
