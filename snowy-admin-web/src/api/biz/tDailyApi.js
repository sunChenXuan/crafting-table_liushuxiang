import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/daily/` + url, ...arg)

/**
 * 日报Api接口管理器
 *
 * @author scx
 * @date  2023/09/20 17:03
 **/
export default {
	// 获取日报分页
	tDailyPage(data) {
		return request('page', data, 'get')
	},
	// 获取日报列表
	tDailyList(data) {
		return request('list', data, 'get')
	},
	// 提交日报表单 edit为true时为编辑，默认为新增
	tDailySubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除日报
	tDailyDelete(data) {
		return request('delete', data)
	},
	// 获取日报详情
	tDailyDetail(data) {
		return request('detail', data, 'get')
	}
}
