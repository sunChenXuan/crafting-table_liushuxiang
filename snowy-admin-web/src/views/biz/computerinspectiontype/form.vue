<template>
	<xn-form-container
		:title="formData.pkId ? '编辑机房巡检类型' : '增加机房巡检类型'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="巡检类型名称：" name="inspectionTypeName">
				<a-select v-model:value="formData.inspectionTypeName" placeholder="请选择巡检类型名称" :options="inspectionTypeNameOptions" />
			</a-form-item>
			<a-form-item label="检查细节：" name="inspectionDetail">
				<a-input v-model:value="formData.inspectionDetail" placeholder="请输入检查细节" allow-clear />
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="tComputerInspectionTypeForm">
	import tool from '@/utils/tool'
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import tComputerInspectionTypeApi from '@/api/biz/tComputerInspectionTypeApi'
	// 抽屉状态
	const visible = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	// 表单数据
	const formData = ref({})
	const submitLoading = ref(false)
	const inspectionTypeNameOptions = ref([])

	// 打开抽屉
	const onOpen = (record) => {
		visible.value = true
		if (record) {
			let recordData = cloneDeep(record)
			formData.value = Object.assign({}, recordData)
		}
		inspectionTypeNameOptions.value = tool.dictList('GENDER')
	}
	// 关闭抽屉
	const onClose = () => {
		formRef.value.resetFields()
		formData.value = {}
		visible.value = false
	}
	// 默认要校验的
	const formRules = {
		inspectionTypeName: [required('请输入巡检类型名称')],
	}
	// 验证并提交数据
	const onSubmit = () => {
		formRef.value.validate().then(() => {
			submitLoading.value = true
			const formDataParam = cloneDeep(formData.value)
			tComputerInspectionTypeApi
				.tComputerInspectionTypeSubmitForm(formDataParam, formDataParam.pkId)
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
