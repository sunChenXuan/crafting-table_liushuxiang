<template>
	<xn-form-container :title="formData.pkId ? '编辑日报' : '增加日报'" :width="700" :visible="visible" :destroy-on-close="true"
		@close="onClose">
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<!-- <a-form-item label="项目名称：" name="objectName">
				<a-input v-model:value="formData.objectName" placeholder="请输入项目名称" allow-clear />
			</a-form-item> -->
			<a-form-item label="时间：" name="time">
				<a-date-picker v-model:value="formData.time" value-format="YYYY-MM-DD HH:mm:ss" show-time
					placeholder="请选择时间" style="width: 100%" />
			</a-form-item>
			<!-- <a-form-item label="经度：" name="longitude">
				<a-input v-model:value="formData.longitude" placeholder="请输入经度" allow-clear />
			</a-form-item>
			<a-form-item label="纬度：" name="latitude">
				<a-input v-model:value="formData.latitude" placeholder="请输入纬度" allow-clear />
			</a-form-item> -->
			<a-form-item label="内容：" name="remark">
				<a-textarea v-model:value="formData.remark" placeholder="请输入内容" allow-clear
					:auto-size="{ minRows: 5, maxRows: 10 }" showCount :maxlength=255 />
			</a-form-item>
			<a-form-item label="创建人：" name="createUserName">
				<a-input disabled v-model:value="formData.createUserName" placeholder="创建人系统自动填充" allow-clear />
			</a-form-item>
			<a-form-item label="联系电话：" name="createUserPhone">
				<a-input disabled v-model:value="formData.createUserPhone" placeholder="联系电话系统自动填充" allow-clear />
			</a-form-item>
			<!-- <a-form-item label="创建用户：" name="createdBy">
				<a-input v-model:value="formData.createdBy" placeholder="请输入创建用户" allow-clear />
			</a-form-item>
			<a-form-item label="修改时间：" name="updatedTime">
				<a-date-picker v-model:value="formData.updatedTime" value-format="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择修改时间" style="width: 100%" />
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

<script setup name="tDailyForm">
import { cloneDeep } from 'lodash-es'
import { required } from '@/utils/formRules'
import tDailyApi from '@/api/biz/tDailyApi'
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })
const formRef = ref()
// 表单数据
const formData = ref({})
const submitLoading = ref(false)

// 打开抽屉
const onOpen = (record) => {
	visible.value = true
	if (record) {
		let recordData = cloneDeep(record)
		formData.value = Object.assign({}, recordData)
	}
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
		tDailyApi
			.tDailySubmitForm(formDataParam, formDataParam.pkId)
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
