<template>
	<xn-form-container :title="formData.pkId ? '编辑客户' : '增加客户'" :width="700" :visible="visible" :destroy-on-close="true"
		@close="onClose">
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="客户名称：" name="customerName">
				<a-input v-model:value="formData.customerName" placeholder="请输入客户名称" allow-clear />
			</a-form-item>
			<a-form-item label="客户类型：" name="customerType">
				<a-checkbox-group v-model:value="formData.customerType" placeholder="请选择客户类型"
					:options="customerTypeOptions" />
			</a-form-item>
			<a-form-item label="联系方式：" name="customerPhone">
				<a-input v-model:value="formData.customerPhone" placeholder="请输入联系方式" allow-clear />
			</a-form-item>
			<a-form-item label="地址：" name="customerAddress">
				<a-input v-model:value="formData.customerAddress" placeholder="请输入地址" allow-clear />
			</a-form-item>
			<a-form-item label="客户画像：" name="customerProfile">
				<a-textarea v-model:value="formData.customerProfile" placeholder="请输入客户画像" allow-clear
					:auto-size="{ minRows: 5, maxRows: 10 }" showCount :maxlength=255 />
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="tCustomerForm">
import tool from '@/utils/tool'
import { cloneDeep } from 'lodash-es'
import { required } from '@/utils/formRules'
import tCustomerApi from '@/api/biz/tCustomerApi'
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })
const formRef = ref()
// 表单数据
const formData = ref({})
const submitLoading = ref(false)
const customerTypeOptions = ref([])

// 打开抽屉
const onOpen = (record) => {
	visible.value = true
	if (record) {
		let recordData = cloneDeep(record)
		recordData.customerType = JSON.parse(recordData.customerType)
		formData.value = Object.assign({}, recordData)
	}
	customerTypeOptions.value = tool.dictList('CUSTOMER_TYPE')
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
		formDataParam.customerType = JSON.stringify(formDataParam.customerType)
		tCustomerApi
			.tCustomerSubmitForm(formDataParam, formDataParam.pkId)
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
