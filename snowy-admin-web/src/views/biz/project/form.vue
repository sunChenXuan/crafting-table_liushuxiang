<template>
	<xn-form-container
		:title="formData.pkId ? '编辑项目' : '增加项目'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
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
				<a-checkbox-group v-model:value="formData.projectHeadUsers" placeholder="请选择负责人" :options="projectHeadUsersOptions" />
			</a-form-item>
			<a-form-item label="职工：" name="projectUsers">
				<a-checkbox-group v-model:value="formData.projectUsers" placeholder="请选择职工" :options="projectUsersOptions" />
			</a-form-item>
			<a-form-item label="项目沟通：" name="projectCommunication">
				<a-input v-model:value="formData.projectCommunication" placeholder="请输入项目沟通" allow-clear />
			</a-form-item>
			<a-form-item label="项目风险：" name="projectRisk">
				<a-input v-model:value="formData.projectRisk" placeholder="请输入项目风险" allow-clear />
			</a-form-item>
			<a-form-item label="采购管理：" name="purchasingManagement">
				<a-input v-model:value="formData.purchasingManagement" placeholder="请输入采购管理" allow-clear />
			</a-form-item>
			<a-form-item label="项目开始时间：" name="projectStartTime">
				<a-date-picker v-model:value="formData.projectStartTime" value-format="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择项目开始时间" style="width: 100%" />
			</a-form-item>
			<a-form-item label="项目结束时间：" name="projectEndTime">
				<a-date-picker v-model:value="formData.projectEndTime" value-format="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择项目结束时间" style="width: 100%" />
			</a-form-item>
			<a-form-item label="项目成本：" name="projectCost">
				<a-input v-model:value="formData.projectCost" placeholder="请输入项目成本" allow-clear />
			</a-form-item>
			<a-form-item label="项目质量：" name="projectQuality">
				<a-input v-model:value="formData.projectQuality" placeholder="请输入项目质量" allow-clear />
			</a-form-item>
			<a-form-item label="项目收获：" name="projectDelivery">
				<a-input v-model:value="formData.projectDelivery" placeholder="请输入项目收获" allow-clear />
			</a-form-item>
			<a-form-item label="存在问题：" name="existingProblems">
				<a-input v-model:value="formData.existingProblems" placeholder="请输入存在问题" allow-clear />
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

<script setup name="tProjectForm">
	import tool from '@/utils/tool'
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import tProjectApi from '@/api/biz/tProjectApi'
	// 抽屉状态
	const visible = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	// 表单数据
	const formData = ref({})
	const submitLoading = ref(false)
	const projectHeadUsersOptions = ref([])
	const projectUsersOptions = ref([])

	// 打开抽屉
	const onOpen = (record) => {
		visible.value = true
		if (record) {
			let recordData = cloneDeep(record)
			recordData.projectHeadUsers = JSON.parse(recordData.projectHeadUsers)
			recordData.projectUsers = JSON.parse(recordData.projectUsers)
			formData.value = Object.assign({}, recordData)
		}
		projectHeadUsersOptions.value = tool.dictList('GENDER')
		projectUsersOptions.value = tool.dictList('GENDER')
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
		projectPhone: [required('请输入联系电话')],
		projectHeadUsers: [required('请输入负责人')],
		projectUsers: [required('请输入职工')],
		projectCommunication: [required('请输入项目沟通')],
		projectRisk: [required('请输入项目风险')],
		purchasingManagement: [required('请输入采购管理')],
		projectStartTime: [required('请输入项目开始时间')],
		projectEndTime: [required('请输入项目结束时间')],
		projectCost: [required('请输入项目成本')],
		projectQuality: [required('请输入项目质量')],
		projectDelivery: [required('请输入项目收获')],
		existingProblems: [required('请输入存在问题')],
	}
	// 验证并提交数据
	const onSubmit = () => {
		formRef.value.validate().then(() => {
			submitLoading.value = true
			const formDataParam = cloneDeep(formData.value)
			formDataParam.projectHeadUsers = JSON.stringify(formDataParam.projectHeadUsers)
			formDataParam.projectUsers = JSON.stringify(formDataParam.projectUsers)
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
