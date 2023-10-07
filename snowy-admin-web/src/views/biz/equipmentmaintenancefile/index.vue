<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="序列号" name="idxEquipmentMaintenanceId">
						<a-input disabled v-model:value="route.query.serialNumber" placeholder="请在设备维保选择设备" />
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
					<a-button type="primary" @click="formRef.onOpen(route.query.pkId)"
						v-if="hasPerm('tProjectFileAdd') && route.query.pkId">
						<template #icon><plus-outlined /></template>
						上传文件
					</a-button>
					<xn-batch-delete v-if="hasPerm('tEquipmentMaintenanceFileBatchDelete')"
						:selectedRowKeys="selectedRowKeys" @batchDelete="deleteBatchTEquipmentMaintenanceFile" />
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'devFileName'">
					{{ record.devFile.name }}
				</template>

				<template v-if="column.dataIndex === 'devFileThumbnail'">
					<img :src="record.devFile.thumbnail" class="record-img" v-if="record.devFile.suffix.toLowerCase() === 'png' ||
						record.devFile.suffix.toLowerCase() === 'jpg' ||
						record.devFile.suffix.toLowerCase() === 'jpng' ||
						record.devFile.suffix.toLowerCase() === 'ico' ||
						record.devFile.suffix.toLowerCase() === 'gif'
						" />
					<img src="/src/assets/images/fileImg/docx.png" class="record-img"
						v-else-if="record.devFile.suffix.toLowerCase() === 'doc' || record.devFile.suffix.toLowerCase() === 'docx'" />
					<img src="/src/assets/images/fileImg/xlsx.png" class="record-img"
						v-else-if="record.devFile.suffix.toLowerCase() === 'xls' || record.devFile.suffix.toLowerCase() === 'xlsx'" />
					<img src="/src/assets/images/fileImg/zip.png" class="record-img"
						v-else-if="record.devFile.suffix.toLowerCase() === 'zip'" />
					<img src="/src/assets/images/fileImg/rar.png" class="record-img"
						v-else-if="record.devFile.suffix.toLowerCase() === 'rar'" />
					<img src="/src/assets/images/fileImg/ppt.png" class="record-img"
						v-else-if="record.devFile.suffix.toLowerCase() === 'ppt' || record.devFile.suffix.toLowerCase() === 'pptx'" />
					<img src="/src/assets/images/fileImg/txt.png" class="record-img" v-else-if="record.suffix === 'txt'" />
					<img src="/src/assets/images/fileImg/html.png" class="record-img"
						v-else-if="record.devFile.suffix.toLowerCase() === 'html'" />
					<img src="/src/assets/images/fileImg/file.png" class="record-img" v-else />
				</template>

				<template v-if="column.dataIndex === 'devFileSizeInfo'">
					{{ record.devFile.sizeInfo }}
				</template>
				<template v-if="column.dataIndex === 'devFileSuffix'">
					{{ record.devFile.suffix }}
				</template>
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(route.query, record)"
							v-if="hasPerm('tEquipmentMaintenanceFileEdit')">编辑</a>
						<a-divider type="vertical"
							v-if="hasPerm(['tEquipmentMaintenanceFileEdit', 'tEquipmentMaintenanceFileDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteTEquipmentMaintenanceFile(record)">
							<a-button type="link" danger size="small"
								v-if="hasPerm('tEquipmentMaintenanceFileDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="equipmentmaintenancefile">
import Form from './form.vue'
import tEquipmentMaintenanceFileApi from '@/api/biz/tEquipmentMaintenanceFileApi'
import { useRoute } from 'vue-router'
let searchFormState = reactive({})
const searchFormRef = ref()
const table = ref()
const formRef = ref()
const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
const columns = [
	{
		title: '设备名称',
		dataIndex: 'equipmentName'
	},
	{
		title: '设备序列号',
		dataIndex: 'serialNumber'
	},
	{
		title: '文件名称',
		dataIndex: 'devFileName',
		ellipsis: true,
		width: '280px'
	},
	{
		title: '缩略图',
		dataIndex: 'devFileThumbnail',
		ellipsis: true,
		width: '80px'
	},
	{
		title: '文件大小',
		dataIndex: 'devFileSizeInfo',
		ellipsis: true
	},
	{
		title: '文件后缀',
		dataIndex: 'devFileSuffix',
		ellipsis: true
	},
]
// 操作栏通过权限判断是否显示
if (hasPerm(['tEquipmentMaintenanceFileEdit', 'tEquipmentMaintenanceFileDelete'])) {
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
	searchFormState.idxEquipmentMaintenanceId = route.query.pkId
	const searchFormParam = JSON.parse(JSON.stringify(searchFormState))
	return tEquipmentMaintenanceFileApi.tEquipmentMaintenanceFilePage(Object.assign(parameter, searchFormParam)).then((data) => {
		return data
	})
}
// 重置
const reset = () => {
	searchFormRef.value.resetFields()
	table.value.refresh(true)
}
// 删除
const deleteTEquipmentMaintenanceFile = (record) => {
	let params = [
		{
			pkId: record.pkId
		}
	]
	tEquipmentMaintenanceFileApi.tEquipmentMaintenanceFileDelete(params).then(() => {
		table.value.refresh(true)
	})
}
// 批量删除
const deleteBatchTEquipmentMaintenanceFile = (params) => {
	tEquipmentMaintenanceFileApi.tEquipmentMaintenanceFileDelete(params).then(() => {
		table.value.clearRefreshSelected()
	})
}

// 获取路由传参
const route = useRoute()
</script>


<style scoped>
.record-img {
	width: 40px;
	height: 40px;
}
</style>
