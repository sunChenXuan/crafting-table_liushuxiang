import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/fixedassetflow/` + url, ...arg)

/**
 * 固定资产借还流水Api接口管理器
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
export default {
	// 获取固定资产借还流水分页
	tFixedAssetFlowPage(data) {
		return request('page', data, 'get')
	},
	// 获取固定资产借还流水列表
	tFixedAssetFlowList(data) {
		return request('list', data, 'get')
	},
	// 提交固定资产借还流水表单 edit为true时为编辑，默认为新增
	tFixedAssetFlowSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除固定资产借还流水
	tFixedAssetFlowDelete(data) {
		return request('delete', data)
	},
	// 获取固定资产借还流水详情
	tFixedAssetFlowDetail(data) {
		return request('detail', data, 'get')
	}
}
