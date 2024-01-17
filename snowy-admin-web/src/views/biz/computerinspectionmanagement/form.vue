<template>
	<xn-form-container
		:title="formData.pkId ? '编辑机房巡检管理' : '增加机房巡检管理'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="项目名称：" name="inspectionName">
				<a-input v-model:value="formData.inspectionName" placeholder="请输入项目名称" allow-clear />
			</a-form-item>
			<a-form-item label="巡检人员：" name="inspectionUsers">
				<a-input v-model:value="formData.inspectionUsers" placeholder="请输入巡检人员" allow-clear />
			</a-form-item>
			<a-form-item label="经度：" name="longitude">
				<a-input v-model:value="formData.longitude" placeholder="请输入经度" allow-clear />
			</a-form-item>
			<a-form-item label="纬度：" name="latitude">
				<a-input v-model:value="formData.latitude" placeholder="请输入纬度" allow-clear />
			</a-form-item>
			<a-form-item label="巡检项目：" name="inspectionType">
				<a-input v-model:value="formData.inspectionType" placeholder="请输入巡检项目" allow-clear />
			</a-form-item>
			<a-form-item label="巡检报告：" name="remarkReport">
				<a-input v-model:value="formData.remarkReport" placeholder="请输入巡检报告" allow-clear />
			</a-form-item>
			<a-form-item label="作业计划：" name="workPlan">
				<a-input v-model:value="formData.workPlan" placeholder="请输入作业计划" allow-clear />
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="tComputerInspectionManagementForm">
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import tComputerInspectionManagementApi from '@/api/biz/tComputerInspectionManagementApi'
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
			tComputerInspectionManagementApi
				.tComputerInspectionManagementSubmitForm(formDataParam, formDataParam.pkId)
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
