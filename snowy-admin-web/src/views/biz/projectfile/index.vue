<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="项目id" name="idxProjectId">
						<a-input v-model:value="searchFormState.idxProjectId" placeholder="请输入项目id" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="文件id" name="ukFileId">
						<a-input v-model:value="searchFormState.ukFileId" placeholder="请输入文件id" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="文档类型" name="projectFileType">
						<a-select v-model:value="searchFormState.projectFileType" placeholder="请选择文档类型" :options="projectFileTypeOptions" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-button type="primary" @click="table.refresh(true)">查询</a-button>
					<a-button style="margin: 0 8px" @click="reset">重置</a-button>
				</a-col>
			</a-row>
		</a-form>
		<s-table
			ref="table"
			:columns="columns"
			:data="loadData"
			:alert="options.alert.show"
			bordered
			:row-key="(record) => record.pkId"
			:tool-config="toolConfig"
			:row-selection="options.rowSelection"
		>
			<template #operator class="table-operator">
				<a-space>
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('tProjectFileAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('tProjectFileBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchTProjectFile"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'projectFileType'">
					{{ $TOOL.dictTypeData('GENDER', record.projectFileType) }}
				</template>
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('tProjectFileEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['tProjectFileEdit', 'tProjectFileDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteTProjectFile(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('tProjectFileDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="projectfile">
	import tool from '@/utils/tool'
	import Form from './form.vue'
	import tProjectFileApi from '@/api/biz/tProjectFileApi'
	let searchFormState = reactive({})
	const searchFormRef = ref()
	const table = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	const columns = [
		{
			title: '项目id',
			dataIndex: 'idxProjectId'
		},
		{
			title: '文件id',
			dataIndex: 'ukFileId'
		},
		{
			title: '文档类型',
			dataIndex: 'projectFileType'
		},
		{
			title: '创建用户',
			dataIndex: 'createdBy'
		},
		{
			title: '修改时间',
			dataIndex: 'updatedTime'
		},
		{
			title: '修改用户',
			dataIndex: 'updatedBy'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['tProjectFileEdit', 'tProjectFileDelete'])) {
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
		return tProjectFileApi.tProjectFilePage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteTProjectFile = (record) => {
		let params = [
			{
				pkId: record.pkId
			}
		]
		tProjectFileApi.tProjectFileDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchTProjectFile = (params) => {
		tProjectFileApi.tProjectFileDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
	const projectFileTypeOptions = tool.dictList('PROJECT_FILE_TYPE')
</script>
