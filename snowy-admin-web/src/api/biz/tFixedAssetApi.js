import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/fixedasset/` + url, ...arg)

/**
 * 固定资产Api接口管理器
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
export default {
	// 获取固定资产分页
	tFixedAssetPage(data) {
		return request('page', data, 'get')
	},
	// 获取固定资产列表
	tFixedAssetList(data) {
		return request('list', data, 'get')
	},
	// 提交固定资产表单 edit为true时为编辑，默认为新增
	tFixedAssetSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除固定资产
	tFixedAssetDelete(data) {
		return request('delete', data)
	},
	// 获取固定资产详情
	tFixedAssetDetail(data) {
		return request('detail', data, 'get')
	}
}
