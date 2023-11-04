<template>
	<xn-form-container :title="formData.pkId ? '编辑机房巡检' : '增加机房巡检'" :width="700" :visible="visible" :destroy-on-close="true"
		@close="onClose">
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="项目名称：" name="inspectionName">
				<a-input v-model:value="formData.inspectionName" placeholder="请输入项目名称" allow-clear />
			</a-form-item>
			<a-form-item label="项目名称：" name="idxProjectId">
				<a-select showSearch v-model:value="formData.inspectionName" placeholder="请选择项目" optionFilterProp="label"
					:options="projectList" />
			</a-form-item>
			<a-form-item label="巡检人员：" name="inspectionUsers">
				<a-checkbox-group v-model:value="formData.inspectionUsers" placeholder="请选择巡检人员"
					:options="inspectionUsersOptions" />
			</a-form-item>
			<!-- <a-form-item label="经度：" name="longitude">
				<a-input v-model:value="formData.longitude" placeholder="请输入经度" allow-clear />
			</a-form-item>
			<a-form-item label="纬度：" name="latitude">
				<a-input v-model:value="formData.latitude" placeholder="请输入纬度" allow-clear />
			</a-form-item> -->
			<a-form-item label="巡检项目：" name="inspectionType">
				<a-input v-model:value="formData.inspectionType" placeholder="请输入巡检项目" allow-clear />
			</a-form-item>
			<a-form-item label="巡检报告：" name="remarkReport">
				<a-input v-model:value="formData.remarkReport" placeholder="请输入巡检报告" allow-clear />
			</a-form-item>
			<!-- <a-form-item label="作业计划：" name="workPlan">
				<a-input v-model:value="formData.workPlan" placeholder="请输入作业计划" allow-clear />
			</a-form-item>
			<a-form-item label="创建用户：" name="createdBy">
				<a-input v-model:value="formData.createdBy" placeholder="请输入创建用户" allow-clear />
			</a-form-item>
			<a-form-item label="修改时间：" name="updatedTime">
				<a-date-picker v-model:value="formData.updatedTime" value-format="YYYY-MM-DD HH:mm:ss" show-time
					placeholder="请选择修改时间" style="width: 100%" />
			</a-form-item>
			<a-form-item label="修改用户：" name="updatedBy">
				<a-input v-model:value="formData.updatedBy" placeholder="请输入修改用户" allow-clear />
			</a-form-item> -->
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="tComputerInspectionForm">
import tool from '@/utils/tool'
import { cloneDeep } from 'lodash-es'
import tComputerInspectionApi from '@/api/biz/tComputerInspectionApi'
import tProjectApi from '@/api/biz/tProjectApi'
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })
const formRef = ref()
// 表单数据
const formData = ref({})
const submitLoading = ref(false)
const inspectionUsersOptions = ref([])
const projectList = ref([])

// 打开抽屉
const onOpen = (record) => {
	visible.value = true
	if (record) {
		let recordData = cloneDeep(record)
		recordData.inspectionUsers = JSON.parse(recordData.inspectionUsers)
		formData.value = Object.assign({}, recordData)
	} else {
		selectProjectList()
	}
	inspectionUsersOptions.value = tool.dictList('GENDER')
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
// 抛出函数
defineExpose({
	onOpen
})
</script>
