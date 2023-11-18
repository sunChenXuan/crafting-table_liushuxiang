<template>
	<xn-form-container title="上传图片" :width="700" :visible="visible" :destroy-on-close="true" @close="onClose">
		<a-form-item>
			<a-upload list-type="picture-card" :file-list="fileList" :before-upload="beforeUpload" @preview="handlePreview"
				@change="handleChange" :customRequest="customUpload">
				<a-icon type="plus" />
				<div class="ant-upload-text">
					+
				</div>
			</a-upload>
		</a-form-item>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="tCustomerInspectionForm">
import tCustomerInspectionFileApi from '@/api/biz/tCustomerInspectionFileApi.js'
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })
// 表单数据
const customerinspectionId = ref({})
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
}
const customUpload = (e) => {
	let formData = new FormData()
	formData.append('data', customerinspectionId.value)
	formData.append('file', e.file)
	tCustomerInspectionFileApi
		.tCustomerInspectionFileSubmitForm(formData)
		.then(() => {
			selectFiles()
			emit('successful')
		})
		.finally(() => {
		})
};
const selectFiles = () => {
	const searchFormParam = JSON.parse(JSON.stringify({ "idxCustomerInspectionId": customerinspectionId.value }))
	tCustomerInspectionFileApi.tCustomerInspectionFilePage(searchFormParam).then(res => {
		fileList.value = res
	})

};


// 抛出函数
defineExpose({
	onOpen
})
</script>
