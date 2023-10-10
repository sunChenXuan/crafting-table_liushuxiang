<template>
	<xn-form-container :title="'借出设备'" :width="700" :visible="visible" :destroy-on-close="true" @close="onClose">
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="序列号：" name="serialNumber">
				<a-input disabled v-model:value="formData.serialNumber" placeholder="请输入序列号" allow-clear />
			</a-form-item>
			<a-form-item label="备注：" name="remark">
				<a-input v-model:value="formData.remark" placeholder="请输入备注" allow-clear />
			</a-form-item>
			<a-form-item label="选择借出人" name="createdBy">
				<a-button type="primary" @click="openUserSelector">选择借出人</a-button>
				<br />
				<a-tag class="mt-3" v-for="(user, index) in formData.loanee" color="cyan" :key="index">{{
					user.name
				}}</a-tag>
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
	<user-selector-plus ref="userSelectorPlusRef" :org-tree-api="selectorApiFunction.orgTreeApi"
		:user-page-api="selectorApiFunction.userPageApi" @onBack="userBack" />
</template>

<script setup name="tFixedAssetFlowForm">
import { cloneDeep } from 'lodash-es'
import { required } from '@/utils/formRules'
import tFixedAssetFlowApi from '@/api/biz/tFixedAssetFlowApi'
import userApi from '@/api/sys/userApi'
import userCenterApi from '@/api/sys/userCenterApi'
import { message } from 'ant-design-vue'
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
	idxFixedAssetId: [required('请输入固定资产id')],
	remark: [required('请输入备注')],
}
// 验证并提交数据
const onSubmit = () => {
	formRef.value.validate().then(() => {
		submitLoading.value = true
		if (!formData.value.loanee || formData.value.loanee.length < 1) {
			message.warning('未选择借出人')
			submitLoading.value = false
			return
		}
		convFormData()
		formData.value.idxFixedAssetId = formData.value.pkId
		const formDataParam = cloneDeep(formData.value)
		formDataParam.loanee = JSON.stringify(formDataParam.loanee)
		tFixedAssetFlowApi
			.tFixedAssetFlowSubmitForm(formDataParam)
			.then(() => {
				onClose()
				emit('successful')
			})
			.finally(() => {
				submitLoading.value = false
			})
	})
}
// 回显需要
const userSelectorPlusRef = ref()
// 打开人员选择器
const openUserSelector = () => {
	userSelectorPlusRef.value.showUserPlusModal(formData.value.loanee)
}
// 人员选择回调
const userBack = (value) => {
	formData.value.loanee = value
}
// 添加接收人
const convFormData = () => {
	let ids = []
	formData.value.loanee.forEach((item) => {
		ids.push(item.id)
	})
	formData.value.loanee = ids
}
// 传递设计器需要的API
const selectorApiFunction = {
	orgTreeApi: (param) => {
		return userApi.userOrgTreeSelector(param).then((data) => {
			return Promise.resolve(data)
		})
	},
	userPageApi: (param) => {
		return userApi.userSelector(param).then((data) => {
			return Promise.resolve(data)
		})
	},
	checkedUserListApi: (param) => {
		return userCenterApi.userCenterGetUserListByIdList(param).then((data) => {
			return Promise.resolve(data)
		})
	}
}

// 抛出函数
defineExpose({
	onOpen
})
</script>
