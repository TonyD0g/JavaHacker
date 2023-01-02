package org.sec;

import org.apache.log4j.Logger;
import org.sec.input.Logo;
import org.sec.start.Application;

//                          _ooOoo_
//                         o8888888o
//                         88" . "88
//                         (| -_- |)
//                          O\ = /O
//                      ____/`---'\____
//                    .   ' \\| |// `.
//                     / \\||| : |||// \
//                   / _||||| -:- |||||- \
//                     | | \\\ - /// | |
//                   | \_| ''\---/'' | |
//                    \ .-\__ `-` ___/-. /
//                 ___`. .' /--.--\ `. . __
//              ."" '< `.___\_<|>_/___.' >'"".
//             | | : `- \`.;`\ _ /`;.`/ - ` : | |
//               \ \ `-. \_ __\ /__ _/ .-` / /
//       ======`-.____`-.___\_____/___.-`____.-'======
//                          `=---='
//
//       .............................................
//                佛祖保佑           永无BUG
//
//                写字楼里写字间，写字间里程序员；
//                程序人员写程序，又拿程序换酒钱。
//                酒醒只在网上坐，酒醉还来网下眠；
//                酒醉酒醒日复日，网上网下年复年。
//                但愿老死电脑间，不愿鞠躬老板前；
//                奔驰宝马贵者趣，公交自行程序员。
//                别人笑我忒疯癫，我笑自己命太贱；
//                不见满街漂亮妹，哪个归得程序员？
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        // 输出logo
        Logo.PrintLogo();
        logger.info("start jsp horse application");
        logger.info("please wait 30 second...");

        // 运行主逻辑
        Application.start(args);
    }
}

