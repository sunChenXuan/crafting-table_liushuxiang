<template>
	<xn-form-container :title="formData.pkId ? '文件修改' : '文件上传'" :width="700" :visible="visible" :destroy-on-close="true"
		@close="onClose">
		<a-tabs v-model:activeKey="activeKey">
			<a-tab-pane key="Local">
				<a-spin :spinning="uploadLoading">
					<a-upload-dragger :show-upload-list="false" :custom-request="customRequest">
						<p class="ant-upload-drag-icon">
							<inbox-outlined></inbox-outlined>
						</p>
						<p class="ant-upload-text">单击或拖动文件到此区域进行上传</p>
						<p class="ant-upload-hint">支持单个上传</p>
					</a-upload-dragger>
				</a-spin>
			</a-tab-pane>
		</a-tabs>
	</xn-form-container>
</template>

<script setup name="tProjectFileForm">
import tool from '@/utils/tool'
import { cloneDeep } from 'lodash-es'
import tEquipmentMaintenanceFileApi from '@/api/biz/tEquipmentMaintenanceFileApi'
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })
// 表单数据
const formData = ref({})
let idxEquipmentMaintenanceId = {}
const projectFileTypeOptions = ref([])
// 打开抽屉
const onOpen = (routeValue, record) => {
	visible.value = true
	idxEquipmentMaintenanceId = routeValue
	if (record) {
		let recordData = cloneDeep(record)
		formData.value = Object.assign({}, recordData)
	}
	projectFileTypeOptions.value = tool.dictList('PROJECT_FILE_TYPE')
}
// 关闭抽屉
const onClose = () => {
	formData.value = {}
	visible.value = false
}
// 默认要校验的
const formRules = {
}

// 文件上传
const uploadLoading = ref(false)
const activeKey = ref('Local')
// 上传文件
const customRequest = (data) => {
	uploadLoading.value = true
	const fileData = new FormData()
	fileData.append('file', data.file)
	const json = {
		"pkId": formData.value.pkId ? formData.value.pkId : null,
		"idxEquipmentMaintenanceId": formData.value.idxEquipmentMaintenanceId ? formData.value.idxEquipmentMaintenanceId : idxEquipmentMaintenanceId,
	}
	fileData.append('data', JSON.stringify(json))
	tEquipmentMaintenanceFileApi
		.tEquipmentMaintenanceFileSubmitForm(fileData, formData.value.pkId)
		.then(() => {
			onClose()
			emit('successful')
		})
		.finally(() => {
			uploadLoading.value = false
		})
}
// 抛出函数
defineExpose({
	onOpen
})
</script>
