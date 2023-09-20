import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/refundflow/` + url, ...arg)

/**
 * 续费流水Api接口管理器
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
export default {
	// 获取续费流水分页
	tRefundFlowPage(data) {
		return request('page', data, 'get')
	},
	// 获取续费流水列表
	tRefundFlowList(data) {
		return request('list', data, 'get')
	},
	// 提交续费流水表单 edit为true时为编辑，默认为新增
	tRefundFlowSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除续费流水
	tRefundFlowDelete(data) {
		return request('delete', data)
	},
	// 获取续费流水详情
	tRefundFlowDetail(data) {
		return request('detail', data, 'get')
	}
}
