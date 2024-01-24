<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="巡检类型名称" name="inspectionTypeName">
						<a-input v-model:value="searchFormState.inspectionTypeName" placeholder="请输入巡检类型名称" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="检查细节" name="inspectionDetail">
						<a-input v-model:value="searchFormState.inspectionDetail" placeholder="请输入检查细节" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-button type="primary" @click="table.refresh(true)">查询</a-button>
					<a-button style="margin: 0 8px" @click="reset">重置</a-button>
				</a-col>
			</a-row>
		</a-form>
		<s-table ref="table" :columns="columns" :data="loadData" :alert="options.alert.show" bordered
			:row-key="(record) => record.pkId" :tool-config="toolConfig" :row-selection="options.rowSelection">
			<template #operator class="table-operator">
				<a-space>
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('tComputerInspectionTypeAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete v-if="hasPerm('tComputerInspectionTypeBatchDelete')" :selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchTComputerInspectionType" />
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'inspectionDetail'">
					<!-- <a-tag v-for="textValue in record.inspectionDetail" :key="textValue" color="green">
						{{ textValue }}
					</a-tag> -->
					<a-tag v-for="item in Object.entries(record.inspectionDetail)" :key="key" color="green">
						{{ item[0] + "　" + (item[1] === "true" ? "布尔" : "文本") }}
					</a-tag>
				</template>
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('tComputerInspectionTypeEdit')">编辑</a>
						<a-divider type="vertical"
							v-if="hasPerm(['tComputerInspectionTypeEdit', 'tComputerInspectionTypeDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteTComputerInspectionType(record)">
							<a-button type="link" danger size="small"
								v-if="hasPerm('tComputerInspectionTypeDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="computerinspectiontype">
import Form from './form.vue'
import tComputerInspectionTypeApi from '@/api/biz/tComputerInspectionTypeApi'
let searchFormState = reactive({})
const searchFormRef = ref()
const table = ref()
const formRef = ref()
const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
const columns = [
	{
		title: '巡检类型名称',
		dataIndex: 'inspectionTypeName'
	},
	{
		title: '检查细节',
		dataIndex: 'inspectionDetail'
	},
]
// 操作栏通过权限判断是否显示
if (hasPerm(['tComputerInspectionTypeEdit', 'tComputerInspectionTypeDelete'])) {
	columns.push({
		title: '操作',
		dataIndex: 'action',
		align: 'center',
		width: '150px'
	})
}
const selectedRowKeys = ref([])
// 列表选择配置
const options = {
	// columns数字类型字段加入 needTotal: true 可以勾选自动算账
	alert: {
		show: true,
		clear: () => {
			selectedRowKeys.value = ref([])
		}
	},
	rowSelection: {
		onChange: (selectedRowKey, selectedRows) => {
			selectedRowKeys.value = selectedRowKey
		}
	}
}
const loadData = (parameter) => {
	const searchFormParam = JSON.parse(JSON.stringify(searchFormState))
	return tComputerInspectionTypeApi.tComputerInspectionTypePage(Object.assign(parameter, searchFormParam)).then((data) => {
		return data
	})
}
// 重置
const reset = () => {
	searchFormRef.value.resetFields()
	table.value.refresh(true)
}
// 删除
const deleteTComputerInspectionType = (record) => {
	let params = [
		{
			pkId: record.pkId
		}
	]
	tComputerInspectionTypeApi.tComputerInspectionTypeDelete(params).then(() => {
		table.value.refresh(true)
	})
}
// 批量删除
const deleteBatchTComputerInspectionType = (params) => {
	tComputerInspectionTypeApi.tComputerInspectionTypeDelete(params).then(() => {
		table.value.clearRefreshSelected()
	})
}
</script>
