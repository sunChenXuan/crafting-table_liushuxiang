import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/customerinspection/` + url, ...arg)

/**
 * 客户巡检Api接口管理器
 *
 * @author scx
 * @date  2023/09/20 17:03
 **/
export default {
	// 获取客户巡检分页
	tCustomerInspectionPage(data) {
		return request('page', data, 'get')
	},
	// 获取客户巡检列表
	tCustomerInspectionList(data) {
		return request('list', data, 'get')
	},
	// 提交客户巡检表单 edit为true时为编辑，默认为新增
	tCustomerInspectionSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除客户巡检
	tCustomerInspectionDelete(data) {
		return request('delete', data)
	},
	// 获取客户巡检详情
	tCustomerInspectionDetail(data) {
		return request('detail', data, 'get')
	}
}
