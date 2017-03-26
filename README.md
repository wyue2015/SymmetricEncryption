# 对称加密的使用

------

> * JavaSE 1.8
> * Eclipse Neon

对称加密（Symmetric Encryption）算法就是能将数据加解密，加密的时候用密钥对数据进行加密，解密的时候使用同样的密钥对数据进行解密。<br>
注意：bouncycastle支持更多的填充模式，在相同算法上比JDK实现的版本的密钥长度要长；java的api中仅仅提供了DES,DESede和PBE 3三种对称加密算法密钥材料实现类；

# 对称加密算法DES—Data Encryption Standard
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DES是美国国家标准研究所提出的算法。因为加解密的数据安全性和密钥长度成正比。des的56位的密钥已经形成安全隐患，在1998年之后就很少被采用。但是一些老旧的系统还在使用。因为这个des算法并没有被美国标准委员会公布全部算法，大家一致怀疑被留了后门。所以慢慢就被淘汰掉了。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;后来针对des算法进行了改进，有了三重des算法（DESede）。针对des算法的密钥长度较短以及迭代次数偏少问题做了相应改进，提高了安全强度。不过desede算法处理速度较慢，密钥计算时间较长，加密效率不高问题使得对称加密算法的发展不容乐观。<br><br>

交互过程：<br>
1、消息传递双方约定密钥，通常由消息发送方（甲方）构建密钥通知消息接收方（乙方）；<br>
2、甲方使用密钥对数据记性加密，然后将加密后的数据通过网络传送给乙方；<br>
3、乙方接收到数据，然后使用约定的密钥对数据进行解密；<br>

# 对称加密算法3DES
3重DES的好处：<br>
密钥长度增强；迭代次数提高；比2重DES或4重DES使用更加广泛；<br>
3DES又称作Triple DES或DESede <br>

# 对称加密算法AES--Advanced Encryption Standard
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DES的不安全性以及DESede算法的低效，催生了这个AES算法（advanced Encryption Standard）。这个算法比DES要快，安全性高。密钥建立时间短、灵敏性好、内存需求低。在各个领域应用广泛。目前，AES通常用于移动通信系统以及一些软件的安全外壳。还有一些无线路由器中也是用AES算法构建加密协议。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目前使用最多的加密方式。AES是至今尚未被破解的加密算法。AES是DES的高级替代者。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无政策限制权限文件是指，因为某些国家的进口管制限制，Java发布的运行环境包中的加解密有一定的限制。<br>
关于无政策限制权限文件的问题：<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;因为某些国家的进口管制限制，Java发布的运行环境包中的加解密有一定的限制。比如默认不允许256位密钥的AES加解密，解决方法就是修改策略文件。<br>
官方网站提供了JCE无限制权限策略文件的下载：<br>
JDK6的下载地址：http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html <br>
JDK7的下载地址：http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html <br>
JDK8的下载地址：http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html <br>
下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt。<br>
如果安装了JRE，将两个jar文件放到%JRE_HOME%\lib\security下覆盖原来文件。<br>
如果安装了JDK，将两个jar文件也放到%JDK_HOME%\jre\lib\security下。<br>

# 对称加密算法PBE--Password Base Encryption
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PBE算法（Password Base Encryption，基于口令加密）算法是一种基于口令的加密算法。特点在于口令由用户自己掌握，采用随机数（我们这里叫做 盐）杂凑多重加密等方法保证数据的安全性。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PBE算法没有密钥的概念，把口令当做密钥了。因为密钥长短影响算法安全性，还不方便记忆，这里我们直接换成我们自己常用的口令就大大不同了，便于我们的记忆。但是单纯的口令很容易被字典法给穷举出来，所以我们这里给口令加了点“盐”，这个盐和口令组合，想破解就难了。同时我们将盐和口令合并后用消息摘要算法进行迭代很多次来构建密钥初始化向量的基本材料，使破译更加难了。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PBE算法没构建新的加密算法，就是用了我们常用的对称加密算法，例如AES,DES等算法。<br><br>
模型：<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;假设甲乙双发要传递消息，需要口令和盐还有算法：<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;传统的对称加密算法就是，构建密钥，指定算法，然后发送数据前用密钥加密数据，密钥和加密后的数据一起发送给对方，对方拿着密钥对数据进行解密。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;现在是密钥没有了，我们就用“口令+盐”构造出一个密钥，然后对数据进行加密；<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这里“口令”我们可以随便设置，都可以设置成我们开自己电脑的密码；<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这里“盐”的设置可以采用随机数，可以是甲乙双方约定的数据；<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最终我们的口令和盐都要公布给双方；<br><br>
具体过程：<br>
1、消息传递双方约定口令，这里甲方构建口令；<br>
2、甲方构建口令后，公布给乙方；<br>
3、由口令构建方（甲方）构建本次消息传递使用的盐，其实也可以双方约定一个数据，例如硬盘号，今天的日期等等，不一定非要写个安全算法计算出来，只要双方一致就行；<br>
4、甲方使用口令、盐对数据加密；<br>
5、甲方将盐、加密数据发送给消息接收者（乙方）；<br>
6、乙方用收到的口令、盐（可以是约定的数据）对数据进行解密；<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总体看来口令和盐两边都需要知道。消息传递过程还是需要指定双方的统一算法进行。而这些算法其实还是用的那些常见的对称加密算法；


