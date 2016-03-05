# ScuMingyuanCarManager

##1.注册，登陆，个人信息
##2.预约加油：
###要求：
  A:绑定一个汽车信息 （个人可有多辆汽车）
  B:含有姓名，时间，加油站，加油类型，加油数量（升或金额）等信息。
  C:把数据发送给服务器并存储，生成二维码。此预约订单在APP显示二维码，以便去加油站扫码加油。
  D:APP可查看提交后的预约订单详情
##3.地图实时显示当前汽车位置，并显示周围的加油站
###要求：
  A:以手机为载体作为汽车，实时更新当先位置
  B:并显示周围的加油站和显示加油站相关信息
##4.根据路况选择最优线路
###要求：
  A:起始点为：可输入地址或当前位置
  B:目的地：可输入地址
  C:起始和目的地可互换
  D:给予最优线路，并可实时告知当前位置
##5.维护车辆信息
###要求：
  A:可维护多辆个人汽车。
  B:假设汽车屏幕可提供此车辆当前信息的二维码,可供用户扫码，APP可扫码并把个人汽车信息维护到手机里面。（因此要求参赛者自己生成一个二维码，然后通过手机扫码完成此功能。）
  C:信息包含但不限于：汽车品牌、标志、型号、车牌号码、发动机号、车身级别（几门几座）、里程数、汽油量（%）、发动机性能（好、异常）、变速器性能（好、异常），车灯（好、坏）。
  D:汽车信息也需要维护到服务器端的数据库里。
  E: 要求把以下通知及时推送到手机端
  a.当服务器端的数据库里记录的汽油量少于20%时，给手机发送通知告诉汽车车主该去加油
  b.当服务器端的数据库里记录的里程数每超过15000公里倍数时，给手机发送通知告诉汽车车主需要进行维护
  c.当服务器端的数据库里记录的发动机出现异常、变速器出现异常或车灯有坏的时候，给手机发送通知告诉汽车车主需要进行维修
##6.可播放音乐
###要求：
  A:进入APP的时候，音乐自动播放
  B:出APP的时候，音乐结束
  C:音乐轮播
  D:请选项合适的音乐
##7.交通违章信息
###要求：
  A:通过之前被绑定的车牌号和发动机号，调用（http://www.cheshouye.com/api.html（参考））提供的接口，获得违章信息并明显。
