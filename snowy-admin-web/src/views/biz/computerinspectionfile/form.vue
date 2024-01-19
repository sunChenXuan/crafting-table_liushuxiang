<template>
	<xn-form-container
		:title="formData.pkId ? '编辑机房巡检文件' : '增加机房巡检文件'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="客户巡检id：" name="idxCustomerInspectionId">
				<a-input v-model:value="formData.idxCustomerInspectionId" placeholder="请输入客户巡检id" allow-clear />
			</a-form-item>
			<a-form-item label="文件id：" name="ukFileId">
				<a-input v-model:value="formData.ukFileId" placeholder="请输入文件id" allow-clear />
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="tComputerInspectionFileForm">
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import tComputerInspectionFileApi from '@/api/biz/tComputerInspectionFileApi'
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
	}
	// 验证并提交数据
	const onSubmit = () => {
		formRef.value.validate().then(() => {
			submitLoading.value = true
			const formDataParam = cloneDeep(formData.value)
			tComputerInspectionFileApi
				.tComputerInspectionFileSubmitForm(formDataParam, formDataParam.pkId)
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
