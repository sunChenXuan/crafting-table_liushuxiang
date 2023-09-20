<template>
	<xn-form-container
		:title="formData.pkId ? '编辑固定资产借还流水' : '增加固定资产借还流水'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="固定资产id：" name="idxFixedAssetId">
				<a-input v-model:value="formData.idxFixedAssetId" placeholder="请输入固定资产id" allow-clear />
			</a-form-item>
			<a-form-item label="序列号：" name="serialNumber">
				<a-input v-model:value="formData.serialNumber" placeholder="请输入序列号" allow-clear />
			</a-form-item>
			<a-form-item label="备注：" name="remark">
				<a-input v-model:value="formData.remark" placeholder="请输入备注" allow-clear />
			</a-form-item>
			<a-form-item label="开始时间：" name="startTime">
				<a-date-picker v-model:value="formData.startTime" value-format="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择开始时间" style="width: 100%" />
			</a-form-item>
			<a-form-item label="结束时间：" name="endTime">
				<a-date-picker v-model:value="formData.endTime" value-format="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择结束时间" style="width: 100%" />
			</a-form-item>
			<a-form-item label="是否归还：" name="isReturn">
				<a-select v-model:value="formData.isReturn" placeholder="请选择是否归还" :options="isReturnOptions" />
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

<script setup name="tFixedAssetFlowForm">
	import tool from '@/utils/tool'
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import tFixedAssetFlowApi from '@/api/biz/tFixedAssetFlowApi'
	// 抽屉状态
	const visible = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	// 表单数据
	const formData = ref({})
	const submitLoading = ref(false)
	const isReturnOptions = ref([])

	// 打开抽屉
	const onOpen = (record) => {
		visible.value = true
		if (record) {
			let recordData = cloneDeep(record)
			formData.value = Object.assign({}, recordData)
		}
		isReturnOptions.value = tool.dictList('GENDER')
	}
	// 关闭抽屉
	const onClose = () => {
		formRef.value.resetFields()
		formData.value = {}
		visible.value = false
	}
	// 默认要校验的
	const formRules = {
		idxFixedAssetId: [required('请输入固定资产id')],
		serialNumber: [required('请输入序列号')],
		remark: [required('请输入备注')],
	}
	// 验证并提交数据
	const onSubmit = () => {
		formRef.value.validate().then(() => {
			submitLoading.value = true
			const formDataParam = cloneDeep(formData.value)
			tFixedAssetFlowApi
				.tFixedAssetFlowSubmitForm(formDataParam, formDataParam.pkId)
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
