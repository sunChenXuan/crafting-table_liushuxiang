<template>
	<xn-form-container
		:title="formData.pkId ? '编辑固定资产' : '增加固定资产'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="序列号：" name="serialNumber">
				<a-input v-model:value="formData.serialNumber" placeholder="请输入序列号" allow-clear />
			</a-form-item>
			<a-form-item label="设备类型：" name="fixedAssetType">
				<a-checkbox-group v-model:value="formData.fixedAssetType" placeholder="请选择设备类型" :options="fixedAssetTypeOptions" />
			</a-form-item>
			<a-form-item label="cpu：" name="fixedAssetCpu">
				<a-radio-group v-model:value="formData.fixedAssetCpu" placeholder="请选择cpu" :options="fixedAssetCpuOptions" />
			</a-form-item>
			<a-form-item label="内存：" name="fixedAssetMemory">
				<a-checkbox-group v-model:value="formData.fixedAssetMemory" placeholder="请选择内存" :options="fixedAssetMemoryOptions" />
			</a-form-item>
			<a-form-item label="固态硬盘：" name="fixedAssetSsd">
				<a-checkbox-group v-model:value="formData.fixedAssetSsd" placeholder="请选择固态硬盘" :options="fixedAssetSsdOptions" />
			</a-form-item>
			<a-form-item label="机械硬盘：" name="fixedAssetDisk">
				<a-checkbox-group v-model:value="formData.fixedAssetDisk" placeholder="请选择机械硬盘" :options="fixedAssetDiskOptions" />
			</a-form-item>
			<a-form-item label="显卡：" name="fixedAssetGpu">
				<a-checkbox-group v-model:value="formData.fixedAssetGpu" placeholder="请选择显卡" :options="fixedAssetGpuOptions" />
			</a-form-item>
			<a-form-item label="配件：" name="fixedAssetAccessory">
				<a-input v-model:value="formData.fixedAssetAccessory" placeholder="请输入配件" allow-clear />
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

<script setup name="tFixedAssetForm">
	import tool from '@/utils/tool'
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import tFixedAssetApi from '@/api/biz/tFixedAssetApi'
	// 抽屉状态
	const visible = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	// 表单数据
	const formData = ref({})
	const submitLoading = ref(false)
	const fixedAssetTypeOptions = ref([])
	const fixedAssetCpuOptions = ref([])
	const fixedAssetMemoryOptions = ref([])
	const fixedAssetSsdOptions = ref([])
	const fixedAssetDiskOptions = ref([])
	const fixedAssetGpuOptions = ref([])

	// 打开抽屉
	const onOpen = (record) => {
		visible.value = true
		if (record) {
			let recordData = cloneDeep(record)
			recordData.fixedAssetType = JSON.parse(recordData.fixedAssetType)
			recordData.fixedAssetMemory = JSON.parse(recordData.fixedAssetMemory)
			recordData.fixedAssetSsd = JSON.parse(recordData.fixedAssetSsd)
			recordData.fixedAssetDisk = JSON.parse(recordData.fixedAssetDisk)
			recordData.fixedAssetGpu = JSON.parse(recordData.fixedAssetGpu)
			formData.value = Object.assign({}, recordData)
		}
		fixedAssetTypeOptions.value = tool.dictList('GENDER')
		fixedAssetCpuOptions.value = tool.dictList('GENDER')
		fixedAssetMemoryOptions.value = tool.dictList('GENDER')
		fixedAssetSsdOptions.value = tool.dictList('GENDER')
		fixedAssetDiskOptions.value = tool.dictList('GENDER')
		fixedAssetGpuOptions.value = tool.dictList('GENDER')
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
			formDataParam.fixedAssetType = JSON.stringify(formDataParam.fixedAssetType)
			formDataParam.fixedAssetMemory = JSON.stringify(formDataParam.fixedAssetMemory)
			formDataParam.fixedAssetSsd = JSON.stringify(formDataParam.fixedAssetSsd)
			formDataParam.fixedAssetDisk = JSON.stringify(formDataParam.fixedAssetDisk)
			formDataParam.fixedAssetGpu = JSON.stringify(formDataParam.fixedAssetGpu)
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
