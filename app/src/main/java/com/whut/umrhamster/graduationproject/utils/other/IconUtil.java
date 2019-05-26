package com.whut.umrhamster.graduationproject.utils.other;

import com.whut.umrhamster.graduationproject.R;

public class IconUtil {

    public static int getTypeResourceByInt(int code){
        switch (code){
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
        }
        return R.drawable.icon_classify_unknow;
    }

    public static int getLevelResourceByInt(int level){
        switch (level){
            case 1:
                return R.drawable.info_lv1;
            case 2:
                return R.drawable.info_lv2;
            case 3:
                return R.drawable.info_lv3;
            case 4:
                return R.drawable.info_lv4;
            case 5:
                return R.drawable.info_lv5;
        }
        return -1;
    }
}
