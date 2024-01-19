<template>
	<xn-form-container title="上传图片" :width="700" :visible="visible" :destroy-on-close="true" @close="onClose">
		<a-form-item>
			<a-upload list-type="picture-card" :file-list="fileList" :before-upload="beforeUpload" @preview="handlePreview"
				@change="handleChange" :customRequest="customUpload" :remove="handleRemove">
				<a-icon type="plus" />
				<div>
					+
				</div>
			</a-upload>
			<a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
				<img alt="example" style="width: 100%" :src="previewImage" />
			</a-modal>
		</a-form-item>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="tCustomerInspectionForm">
import tComputerInspectionFileApi from '@/api/biz/tComputerInspectionFileApi'
// 抽屉状态
const visible = ref(false)
const previewVisible = ref(false)
const emit = defineEmits({ successful: null })
// 表单数据
const customerinspectionId = ref({})
const previewImage = ref({})
const fileList = ref([])

// 打开抽屉
const onOpen = (record) => {
	visible.value = true
	if (record) {
		customerinspectionId.value = record.pkId
		selectFiles()
	}
}
// 关闭抽屉
const onClose = () => {
	visible.value = false
}
const handlePreview = (file) => {
	if (!file.url && !file.preview) {
		file.preview = getBase64(file.originFileObj);
	}
	previewImage.value = file.url || file.preview;
	previewVisible.value = true;
}
const customUpload = (e) => {
	let formData = new FormData()
	formData.append('data', customerinspectionId.value)
	formData.append('file', e.file)
	tComputerInspectionFileApi
		.tComputerInspectionFileSubmitForm(formData)
		.then(() => {
			selectFiles()
			emit('successful')
		})
		.finally(() => {
		})
};
const selectFiles = () => {
	const searchFormParam = JSON.parse(JSON.stringify({ "idxCustomerInspectionId": customerinspectionId.value }))
	tComputerInspectionFileApi.tComputerInspectionFilePage(searchFormParam).then(res => {
		fileList.value = res
	})

};
const getBase64 = (file) => {
	return new Promise((resolve, reject) => {
		const reader = new FileReader();
		reader.readAsDataURL(file);
		reader.onload = () => resolve(reader.result);
		reader.onerror = error => reject(error);
	});
}
const handleCancel = () => {
	previewVisible.value = false;
}
const handleRemove = (file) => {
	let params = [
		{
			pkId: file.pkId
		}
	]
	tComputerInspectionFileApi.tComputerInspectionFileDelete(params).then(() => {
		selectFiles()
		emit('successful')
	})
}
// 抛出函数
defineExpose({
	onOpen
})
</script>
