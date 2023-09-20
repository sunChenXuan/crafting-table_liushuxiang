import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/project/` + url, ...arg)

/**
 * 项目Api接口管理器
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
export default {
	// 获取项目分页
	tProjectPage(data) {
		return request('page', data, 'get')
	},
	// 获取项目列表
	tProjectList(data) {
		return request('list', data, 'get')
	},
	// 提交项目表单 edit为true时为编辑，默认为新增
	tProjectSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除项目
	tProjectDelete(data) {
		return request('delete', data)
	},
	// 获取项目详情
	tProjectDetail(data) {
		return request('detail', data, 'get')
	}
}
