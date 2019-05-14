package com.whut.umrhamster.graduationproject.utils.other;

import com.whut.umrhamster.graduationproject.R;

public class ClassifyUtil {
    public static int id2res(int id){
        switch (id){
            case 1001:
                return R.drawable.icon_advanced_math;
            case 1002:
                return R.drawable.icon_classify_dxwl;
            case 1003:
                return R.drawable.icon_xiandai;
            case 1004:
                return R.drawable.icon_classify_czxt;
            case 1005:
                return R.drawable.icon_classify_sfdl;
            case 1006:
                return R.drawable.icon_classify_sjjg;
            case 1007:
                return R.drawable.icon_classify_szfx;
            case 1008:
                return R.drawable.icon_classify_jsjwl;
            case 2001:
                return R.drawable.icon_classify_qdkf;
            case 2002:
                return R.drawable.icon_classify_ydkf;
            case 2003:
                return R.drawable.icon_classify_hdkf;
            case 2004:
                return R.drawable.icon_classify_rgzn;
            case 3001:
                return R.drawable.icon_classify_grlc;
            case 3002:
                return R.drawable.icon_classify_ydjk;
            case 3003:
                return R.drawable.icon_classify_shbk;
            case 3004:
                return R.drawable.icon_classify_sfhh;
            case 3005:
                return R.drawable.icon_classify_xlxk;
            case 3006:
                return R.drawable.icon_classify_syjc;
            default:
                return -1;  //qita
        }
    }
}
