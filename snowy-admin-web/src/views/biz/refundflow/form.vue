<template>
	<xn-form-container
		:title="formData.pkId ? '编辑续费流水' : '增加续费流水'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="设备维保id：" name="idxEquipmentMaintenanceId">
				<a-input v-model:value="formData.idxEquipmentMaintenanceId" placeholder="请输入设备维保id" allow-clear />
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
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="tRefundFlowForm">
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import tRefundFlowApi from '@/api/biz/tRefundFlowApi'
	// 抽屉状态
	const visible = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	// 表单数据
	const formData = ref({})
	const submitLoading = ref(false)

	// 打开抽屉
	const onOpen = (record) => {
		visible.value = true
		if (record) {
			let recordData = cloneDeep(record)
			formData.value = Object.assign({}, recordData)
		}
	}
	// 关闭抽屉
	const onClose = () => {
		formRef.value.resetFields()
		formData.value = {}
		visible.value = false
	}
	// 默认要校验的
	const formRules = {
		serialNumber: [required('请输入序列号')],
		authorizationStartTime: [required('请输入授权开始时间')],
		authorizationEndTime: [required('请输入授权结束时间')],
	}
	// 验证并提交数据
	const onSubmit = () => {
		formRef.value.validate().then(() => {
			submitLoading.value = true
			const formDataParam = cloneDeep(formData.value)
			tRefundFlowApi
				.tRefundFlowSubmitForm(formDataParam, formDataParam.pkId)
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
