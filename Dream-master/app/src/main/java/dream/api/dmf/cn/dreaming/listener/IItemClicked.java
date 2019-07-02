package dream.api.dmf.cn.dreaming.listener;

import android.view.View;

/**
 * Time:2019/7/2
 * <p>
 * Author:tianzc
 * <p>
 * Description: 描述
 */
public interface IItemClicked<T> {
    void onItemClicked(View view, int position, T data);
}
