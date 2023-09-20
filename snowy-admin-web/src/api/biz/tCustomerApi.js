import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/customer/` + url, ...arg)

/**
 * 客户Api接口管理器
 *
 * @author scx
 * @date  2023/09/20 17:03
 **/
export default {
	// 获取客户分页
	tCustomerPage(data) {
		return request('page', data, 'get')
	},
	// 获取客户列表
	tCustomerList(data) {
		return request('list', data, 'get')
	},
	// 提交客户表单 edit为true时为编辑，默认为新增
	tCustomerSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除客户
	tCustomerDelete(data) {
		return request('delete', data)
	},
	// 获取客户详情
	tCustomerDetail(data) {
		return request('detail', data, 'get')
	}
}
