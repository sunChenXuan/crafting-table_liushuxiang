<template>
	<a-card :bordered="false" style="margin-bottom: 10px">
		<a-form ref="searchFormRef" name="advanced_search" class="ant-advanced-search-form" :model="searchFormState">
			<a-row :gutter="24">
				<a-col :span="8">
					<a-form-item name="searchKey" label="名称关键词">
						<a-input v-model:value="searchFormState.searchKey" placeholder="请输入文件名称关键词" />
					</a-form-item>
				</a-col>
				<a-col :span="8">
					<a-form-item name="engine" label="存储位置">
						<a-select v-model:value="searchFormState.engine" placeholder="请选择存储位置" :options="engineOptions" />
					</a-form-item>
				</a-col>
				<a-col :span="8">
					<a-button type="primary" @click="table.refresh(true)">
						<template #icon>
							<SearchOutlined />
						</template>
						查询
					</a-button>
					<a-button class="snowy-buttom-left" @click="reset">
						<template #icon><redo-outlined /></template>
						重置
					</a-button>
				</a-col>
			</a-row>
		</a-form>
	</a-card>
	<a-card :bordered="false">
		<s-table ref="table" :columns="columns" :data="loadData" :expand-row-by-click="true" :alert="options.alert.show"
			bordered :row-key="(record) => record.id" :row-selection="options.rowSelection">
			<template #operator class="table-operator">
				<a-space>
					<a-button type="primary" @click="() => uploadFormRef.openUpload()">
						<UploadOutlined />
						文件上传
					</a-button>
					<xn-batch-delete :selectedRowKeys="selectedRowKeys" @batchDelete="deleteBatchFile" />
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'thumbnail'">
					<img :src="record.thumbnail" class="record-img" v-if="record.suffix.toLowerCase() === 'png' ||
						record.suffix.toLowerCase() === 'jpg' ||
						record.suffix.toLowerCase() === 'jpng' ||
						record.suffix.toLowerCase() === 'ico' ||
						record.suffix.toLowerCase() === 'gif'
						" />
					<img src="/src/assets/images/fileImg/docx.png" class="record-img"
						v-else-if="record.suffix.toLowerCase() === 'doc' || record.suffix.toLowerCase() === 'docx'" />
					<img src="/src/assets/images/fileImg/xlsx.png" class="record-img"
						v-else-if="record.suffix.toLowerCase() === 'xls' || record.suffix.toLowerCase() === 'xlsx'" />
					<img src="/src/assets/images/fileImg/zip.png" class="record-img"
						v-else-if="record.suffix.toLowerCase() === 'zip'" />
					<img src="/src/assets/images/fileImg/rar.png" class="record-img"
						v-else-if="record.suffix.toLowerCase() === 'rar'" />
					<img src="/src/assets/images/fileImg/ppt.png" class="record-img"
						v-else-if="record.suffix.toLowerCase() === 'ppt' || record.suffix.toLowerCase() === 'pptx'" />
					<img src="/src/assets/images/fileImg/txt.png" class="record-img"
						v-else-if="record.suffix.toLowerCase() === 'txt'" />
					<img src="/src/assets/images/fileImg/html.png" class="record-img"
						v-else-if="record.suffix.toLowerCase() === 'html'" />
					<img src="/src/assets/images/fileImg/file.png" class="record-img" v-else />
				</template>
				<template v-if="column.dataIndex === 'engine'">
					{{ $TOOL.dictTypeData('FILE_ENGINE', record.engine) }}
				</template>
				<template v-if="column.dataIndex === 'action'">
					<a @click="detailRef.onOpen(record)">详情</a>
					<a-divider type="vertical" />
					<a :href="record.downloadPath" target="_blank">下载</a>
					<a-divider type="vertical" />
					<a-popconfirm title="删除此文件？" @confirm="deleteFile(record)">
						<a-button type="link" danger size="small">删除</a-button>
					</a-popconfirm>
				</template>
			</template>
		</s-table>
	</a-card>
	<uploadForm ref="uploadFormRef" @successful="table.refresh(true)" />
	<detail ref="detailRef" />
</template>

<script setup name="devFile">
import fileApi from '@/api/dev/fileApi'
import uploadForm from './uploadForm.vue'
import detail from './detail.vue'
import tool from '@/utils/tool'

// 定义tableDOM
const table = ref()
const form = ref()
const searchFormRef = ref()
let searchFormState = reactive({})
const uploadFormRef = ref()
const detailRef = ref()

const columns = [
	{
		title: '文件名称',
		dataIndex: 'name',
		ellipsis: true,
		width: '280px'
	},
	{
		title: '缩略图',
		dataIndex: 'thumbnail',
		ellipsis: true,
		width: '80px'
	},
	{
		title: '文件大小',
		dataIndex: 'sizeInfo',
		ellipsis: true
	},
	{
		title: '文件后缀',
		dataIndex: 'suffix',
		ellipsis: true
	},
	{
		title: '储存引擎',
		dataIndex: 'engine',
		ellipsis: true
	},
	{
		title: '操作',
		dataIndex: 'action',
		align: 'center',
		width: '180px'
	}
]
let selectedRowKeys = ref([])
// 列表选择配置
const options = {
	alert: {
		show: false,
		clear: () => {
			selectedRowKeys = ref([])
		}
	},
	rowSelection: {
		onChange: (selectedRowKey, selectedRows) => {
			selectedRowKeys.value = selectedRowKey
		}
	}
}
// 表格查询 返回 Promise 对象
const loadData = (parameter) => {
	return fileApi.filePage(Object.assign(parameter, searchFormState)).then((data) => {
		return data
	})
}
// 重置
const reset = () => {
	searchFormRef.value.resetFields()
	table.value.refresh(true)
}
// 删除
const deleteFile = (record) => {
	let params = [
		{
			id: record.id
		}
	]
	fileApi.fileDelete(params).then(() => {
		table.value.refresh(true)
	})
}
// 批量删除
const deleteBatchFile = (params) => {
	fileApi.fileDelete(params).then(() => {
		table.value.clearRefreshSelected()
	})
}
// 存储位置
const engineOptions = tool.dictList('FILE_ENGINE')
</script>

<style scoped>
.record-img {
	width: 40px;
	height: 40px;
}

.ant-form-item {
	margin-bottom: 0 !important;
}

.snowy-buttom-left {
	margin-left: 8px;
}
</style>
