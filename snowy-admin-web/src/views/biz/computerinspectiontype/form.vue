<template>
	<xn-form-container :title="formData.pkId ? '编辑机房巡检类型' : '增加机房巡检类型'" :width="700" :visible="visible"
		:destroy-on-close="true" @close="onClose">
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="巡检类型名称：" name="inspectionTypeName">
				<a-input v-model:value="formData.inspectionTypeName" placeholder="请输入巡检类型名称" allow-clear />
			</a-form-item>
			<a-form-item label="" name="inspectionDetail">
				{{ "检查细节" }}
				<a-button size="small" shape="circle" type="primary" style="margin-right: 8px"
					@click="addDefault">+</a-button>
				<a-button size="small" shape="circle" v-if="inspectionDetailArray.length > 1" @click="del()">-</a-button>
				<a-form :inline="true" v-for="(_, index) in inspectionDetailArray" :key="index">
					<a-row style="margin-top: 8px; margin-left: 3px;">
						<a-col :span="18">
							<a-input v-model:value="inspectionDetailArray[index].text" placeholder="请输入检查细节" allow-clear />
						</a-col>
						<a-col :span="6">
							<a-radio-group style="margin-left: 6px;" default-value="true"
								v-model:value="inspectionDetailArray[index].isBool">
								<a-radio-button value=true>
									布尔
								</a-radio-button>
								<a-radio-button value=false>
									文本
								</a-radio-button>
							</a-radio-group>
						</a-col>
					</a-row>
				</a-form>
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="tComputerInspectionTypeForm">
import { cloneDeep } from 'lodash-es'
import { required } from '@/utils/formRules'
import tComputerInspectionTypeApi from '@/api/biz/tComputerInspectionTypeApi'
import { message } from 'ant-design-vue'
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })
const formRef = ref()
// 表单数据
const formData = ref({})
const submitLoading = ref(false)
const inspectionDetailArray = ref([])

// 打开抽屉
const onOpen = (record) => {
	visible.value = true
	if (record) {
		let recordData = cloneDeep(record)
		formData.value = Object.assign({}, recordData)
		Object.entries(formData.value.inspectionDetail).forEach(item => {
			add(item[0], item[1])
		})
	} else {
		addDefault()
	}
}
// 关闭抽屉
const onClose = () => {
	formRef.value.resetFields()
	inspectionDetailArray.value = []
	formData.value = {}
	visible.value = false
}
// 默认要校验的
const formRules = {
	inspectionTypeName: [required('请选择项目名称')],
}
// 验证并提交数据
const onSubmit = () => {
	formRef.value.validate().then(() => {
		submitLoading.value = true
		let map = new Map()
		for (let i = 0; i < inspectionDetailArray.value.length; i++) {
			if (inspectionDetailArray.value[i] && inspectionDetailArray.value[i] != "") {
				if (map.has(inspectionDetailArray.value[i].text)) {
					message.warning('检查细节不可重名')
					submitLoading.value = false
					return
				}
				map.set(inspectionDetailArray.value[i].text, inspectionDetailArray.value[i].isBool)
			}
		}
		if (map.length == 0) {
			message.warning('请完善检查细节')
			submitLoading.value = false
			return
		}
		formData.value.inspectionDetail = JSON.stringify(Object.fromEntries(map))
		const formDataParam = cloneDeep(formData.value)
		tComputerInspectionTypeApi
			.tComputerInspectionTypeSubmitForm(formDataParam, formDataParam.pkId)
			.then(() => {
				onClose()
				emit('successful')
			})
			.finally(() => {
				submitLoading.value = false
			})
	})
}
const add = (text, isBool) => {
	inspectionDetailArray.value.push({
		text: text,
		isBool: isBool
	})
}
const addDefault = () => {
	add("", true)
}
const del = () => {
	if (inspectionDetailArray.value.length > 1) {
		inspectionDetailArray.value.splice(inspectionDetailArray.value.length - 1, 1)
	}
}
// 抛出函数
defineExpose({
	onOpen
})
</script>
