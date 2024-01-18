<template>
	<xn-form-container :title="formData.pkId ? '编辑客户巡检' : '增加客户巡检'" :width="700" :visible="visible" :destroy-on-close="true"
		@close="onClose">
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="项目名称：" name="inspectionName">
				<a-select showSearch v-model:value="formData.inspectionName" placeholder="请选择项目" optionFilterProp="label"
					:options="projectList" />
			</a-form-item>
			<a-form-item label="巡检类型：" name="inspectionType">
				<a-select v-model:value="formData.inspectionType" placeholder="请选择巡检类型" :options="inspectionTypeOptions" />
			</a-form-item>
			<a-form-item label="联系人：" name="userName">
				<a-input disabled v-model:value="formData.userName" placeholder="联系人系统自动填充" allow-clear />
			</a-form-item>
			<a-form-item label="联系电话：" name="phone">
				<a-input disabled v-model:value="formData.phone" placeholder="联系电话系统自动填充" allow-clear />
			</a-form-item>
			<!-- <a-form-item label="开始时间：" name="startTime">
				<a-date-picker v-model:value="formData.startTime" value-format="YYYY-MM-DD HH:mm:ss" show-time
					placeholder="请选择开始时间" style="width: 100%" />
			</a-form-item> -->
			<!-- <a-form-item label="经度：" name="longitude">
				<a-input v-model:value="formData.longitude" placeholder="请输入经度" allow-clear />
			</a-form-item>
			<a-form-item label="纬度：" name="latitude">
				<a-input v-model:value="formData.latitude" placeholder="请输入纬度" allow-clear />
			</a-form-item> -->
			<a-form-item label="巡检内容：" name="remark">
				<!-- <a-input v-model:value="formData.remark" placeholder="请输入巡检内容" allow-clear /> -->
				<a-textarea v-model:value="formData.remark" placeholder="请输入巡检内容" allow-clear
					:auto-size="{ minRows: 5, maxRows: 10 }" showCount :maxlength=255 />
			</a-form-item>
			<!-- <a-form-item label="创建用户：" name="createdBy">
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

<script setup name="tCustomerInspectionForm">
import tool from '@/utils/tool'
import { cloneDeep } from 'lodash-es'
import { required, rules } from '@/utils/formRules'
import tCustomerInspectionApi from '@/api/biz/tCustomerInspectionApi'
import tProjectApi from '@/api/biz/tProjectApi'
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })
const formRef = ref()
// 表单数据
const formData = ref({})
const submitLoading = ref(false)
const inspectionTypeOptions = ref([])

// 打开抽屉
const onOpen = (record) => {
	selectProjectList()
	visible.value = true
	if (record) {
		let recordData = cloneDeep(record)
		formData.value = Object.assign({}, recordData)
	}
	inspectionTypeOptions.value = tool.dictList('INSPECTION_TYPE')
}
// 关闭抽屉
const onClose = () => {
	formRef.value.resetFields()
	formData.value = {}
	visible.value = false
}
// 默认要校验的
const formRules = {
	// phone: [
	// 	required('请输入联系电话'), rules.phone
	// ],
}
// 验证并提交数据
const onSubmit = () => {
	formRef.value.validate().then(() => {
		submitLoading.value = true
		const formDataParam = cloneDeep(formData.value)
		tCustomerInspectionApi
			.tCustomerInspectionSubmitForm(formDataParam, formDataParam.pkId)
			.then(() => {
				onClose()
				emit('successful')
			})
			.finally(() => {
				submitLoading.value = false
			})
	})
}
const projectList = ref([])
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
