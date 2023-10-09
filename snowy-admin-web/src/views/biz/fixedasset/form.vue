<template>
	<xn-form-container :title="formData.pkId ? '编辑固定资产' : '增加固定资产'" :width="700" :visible="visible" :destroy-on-close="true"
		@close="onClose">
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="序列号：" name="serialNumber">
				<a-input v-model:value="formData.serialNumber" placeholder="请输入序列号" allow-clear />
			</a-form-item>
			<a-form-item label="设备类型：" name="fixedAssetType">
				<a-select v-model:value="formData.fixedAssetType" placeholder="请选择设备类型" :options="fixedAssetTypeOptions" />
			</a-form-item>
			<a-form-item label="cpu：" name="fixedAssetCpu">
				<a-input v-model:value="formData.fixedAssetCpu" placeholder="请输入cpu" allow-clear />
			</a-form-item>
			<a-form-item label="内存：" name="fixedAssetMemory">
				<a-input v-model:value="formData.fixedAssetMemory" placeholder="请输入内存" allow-clear />
			</a-form-item>
			<a-form-item label="固态硬盘：" name="fixedAssetSsd">
				<a-input v-model:value="formData.fixedAssetSsd" placeholder="请输入固态硬盘" allow-clear />
			</a-form-item>
			<a-form-item label="机械硬盘：" name="fixedAssetDisk">
				<a-input v-model:value="formData.fixedAssetDisk" placeholder="请输入机械硬盘" allow-clear />
			</a-form-item>
			<a-form-item label="显卡：" name="fixedAssetGpu">
				<a-input v-model:value="formData.fixedAssetGpu" placeholder="请输入显卡" allow-clear />
			</a-form-item>
			<a-form-item label="配件：" name="fixedAssetAccessory">
				<a-textarea v-model:value="formData.fixedAssetAccessory" placeholder="请输入配件" allow-clear
					:auto-size="{ minRows: 5, maxRows: 10 }" />
			</a-form-item>
			<a-form-item v-if="formData.pkId" label="修改备注：" name="remark">
				<a-textarea v-model:value="formData.remark" placeholder="修改备注" allow-clear
					:auto-size="{ minRows: 5, maxRows: 10 }" />
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="tFixedAssetForm">
import tool from '@/utils/tool'
import { cloneDeep } from 'lodash-es'
import tFixedAssetApi from '@/api/biz/tFixedAssetApi'
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })
const formRef = ref()
// 表单数据
const formData = ref({})
const submitLoading = ref(false)
const fixedAssetTypeOptions = ref([])

// 打开抽屉
const onOpen = (record) => {
	visible.value = true
	if (record) {
		let recordData = cloneDeep(record)
		formData.value = Object.assign({}, recordData)
	}
	fixedAssetTypeOptions.value = tool.dictList('FIXED_ASSET_TYPE')
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
		tFixedAssetApi
			.tFixedAssetSubmitForm(formDataParam, formDataParam.pkId)
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
