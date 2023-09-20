import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/fixedassethardwareflow/` + url, ...arg)

/**
 * 固定资产硬件流水Api接口管理器
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
export default {
	// 获取固定资产硬件流水分页
	tFixedAssetHardwareFlowPage(data) {
		return request('page', data, 'get')
	},
	// 获取固定资产硬件流水列表
	tFixedAssetHardwareFlowList(data) {
		return request('list', data, 'get')
	},
	// 提交固定资产硬件流水表单 edit为true时为编辑，默认为新增
	tFixedAssetHardwareFlowSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除固定资产硬件流水
	tFixedAssetHardwareFlowDelete(data) {
		return request('delete', data)
	},
	// 获取固定资产硬件流水详情
	tFixedAssetHardwareFlowDetail(data) {
		return request('detail', data, 'get')
	}
}
