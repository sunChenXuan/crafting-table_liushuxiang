<template>
	<xn-form-container
	:title="formData.pkId ? '文件修改' : '文件上传'"
	:width="700"
	:visible="visible"
	:destroy-on-close="true"
	@close="onClose"
	>
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

<script setup name="tFixedAssetFileForm">
import { cloneDeep } from 'lodash-es'
import tFixedAssetFileApi from '@/api/biz/tFixedAssetFileApi'
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })
// 表单数据
const formData = ref({})
let idxFixedAssetId = {}
// 打开抽屉
const onOpen = (routeValue, record) => {
	visible.value = true
	idxFixedAssetId = routeValue
	if (record) {
		let recordData = cloneDeep(record)
		formData.value = Object.assign({}, recordData)
	}
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
		"idxFixedAssetId": formData.value.idxFixedAssetId ? formData.value.idxFixedAssetId : idxFixedAssetId,
	}
	fileData.append('data', JSON.stringify(json))
	tFixedAssetFileApi
		.tFixedAssetFileSubmitForm(fileData, formData.value.pkId)
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
