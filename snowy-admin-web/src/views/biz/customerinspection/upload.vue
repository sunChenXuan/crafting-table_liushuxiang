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
import tool from '@/utils/tool'
import { cloneDeep } from 'lodash-es'
import { required, rules } from '@/utils/formRules'
import tCustomerInspectionFileApi from '@/api/biz/tCustomerInspectionFileApi.js'
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })
const formRef = ref()
const submitLoading = ref(false)
// 表单数据
const customerinspectionId = ref({})
const fileList = ref([])

// 打开抽屉
const onOpen = (record) => {
	visible.value = true
	if (record) {
		customerinspectionId.value = record.pkId
	}
}
// 关闭抽屉
const onClose = () => {
	formRef.value.resetFields()
	visible.value = false
}
const handlePreview = (file) => {
}

const customUpload = (e) => {
	submitLoading.value = true
	let formData = new FormData()
	// customerinspectionId
	console.log(customerinspectionId.value)
	formData.append('data', customerinspectionId.value)
	formData.append('file', e.file)
	tCustomerInspectionFileApi
		.tCustomerInspectionFileSubmitForm(formData)
		.then(() => {
			emit('successful')
		})
		.finally(() => {
			submitLoading.value = false
		})
};

// 抛出函数
defineExpose({
	onOpen
})
</script>
