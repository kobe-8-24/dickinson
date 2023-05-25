[首页](https://www.alibabacloud.com/help/zh)>[VPN网关](https://www.alibabacloud.com/help/zh/vpn-gateway)>[产品简介](https://www.alibabacloud.com/help/zh/vpn-gateway/latest/product-introduction)>什么是VPN网关

搜索帮助内容

# 什么是VPN网关

更新时间：2022-11-30 16:47

VPN网关是一款网络连接服务，通过建立加密隧道的方式实现企业本地数据中心、企业办公网络、互联网客户端与阿里云专有网络VPC（Virtual Private Cloud）之间安全可靠的私网连接。

**说明** 阿里云VPN网关在国家相关政策法规内提供服务，仅支持建立非跨境连接，不支持建立跨境连接。更多信息，请参见[什么是跨境连接和非跨境连接？](https://www.alibabacloud.com/help/zh/vpn-gateway/latest/faq-about-vpn-gateways#section-ck8-ojc-kci)。

![产品简介-202209-1](https://help-static-aliyun-doc.aliyuncs.com/assets/img/zh-CN/6888152661/p487110.png)

## 功能特性

VPN网关提供IPsec-VPN和SSL-VPN两种网络连接方式，不同的网络连接方式适用于不同的应用场景。

### IPsec-VPN

IPsec-VPN是一种基于路由的网络连接技术，提供灵活的流量路由方式，方便您配置和维护VPN策略，适用于在企业本地数据中心或企业办公网络与VPC之间建立网络连接。

在您创建IPsec-VPN的过程中，依据IPsec连接绑定的资源不同，实现网络连接的方式也不相同，如下图所示。

### 绑定VPN网关实例

![产品简介-202209-4](https://help-static-aliyun-doc.aliyuncs.com/assets/img/zh-CN/0709152661/p487112.png)

### 绑定转发路由器实例

![产品简介-202209-3](https://help-static-aliyun-doc.aliyuncs.com/assets/img/zh-CN/6126739661/p487113.png)

### 绑定VPN网关实例和绑定转发路由器实例功能对比

您可以依据下表中的对比信息，为IPsec连接选择适用的绑定资源，以满足您的场景需求。

| 对比项                      | 绑定VPN网关实例                                              | 绑定转发路由器实例                                           |
| :-------------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| 关联的资源                  | 在您创建IPsec-VPN时，您需购买VPN网关实例，VPN网关实例需关联至一个VPC上。企业本地数据中心或企业办公网络可与关联的VPC直接互通，也可以通过关联的VPC与其他网络互通。 | 在您创建IPsec-VPN时，您无需购买VPN网关实例，也无需关联VPC，您需要使用云企业网CEN（Cloud Enterprise Network）产品，在云企业网下创建转发路由器实例。企业本地数据中心或企业办公网络可通过转发路由器实例与任意VPC互通，也可以与转发路由器实例下的其他网络直接互通。 |
| 支持的加密算法              | 国际标准商用密码算法                                         | 国际标准商用密码算法                                         |
| 单个IPsec连接支持的带宽规格 | 最大支持为1000 Mbps。**说明** 部分地域的VPN网关实例带宽规格最大支持为200 Mbps。关于地域信息，请参见[VPN网关实例使用限制](https://www.alibabacloud.com/help/zh/vpn-gateway/latest/create-a-vpn-gateway#section-jlh-9te-6r0)。 | 默认限制为1 Gbps，可随业务弹性扩展。                         |
| 支持的网络类型              | 公网表示通过互联网建立加密隧道。私网表示基于物理专线的私网连接建立加密隧道。**说明** 私网网络类型的VPN网关正在邀测中，您可以向客户经理申请体验或者[提交工单](https://workorder-intl.console.aliyun.com/?spm=5176.2020520001.nav-right.dticket.59b44bd3QY32s9#/ticket/createIndex)申请体验。 | 公网表示通过互联网建立加密隧道。私网表示基于物理专线的私网连接建立加密隧道。 |
| 实现高可用链路的方式        | 通过主备链路的方式实现链路的高可用。                         | 通过ECMP（Equal-Cost Multipath Routing）方式实现链路的高可用。 |
| 典型应用场景                | 建立VPC与本地数据中心之间的连接建立VPC与VPC之间的连接建立VPC和本地数据中心之间的高可用连接（主备链路）建立多个站点之间的连接实现物理专线私网流量加密通信更多信息，请参见[应用场景](https://www.alibabacloud.com/help/zh/vpn-gateway/latest/scenarios#concept-dqh-ysx-wdb)。 | 建立VPC与本地数据中心之间的连接建立VPC和本地数据中心之间的高可用连接（ECMP链路）建立多个站点之间的连接实现物理专线私网流量加密通信更多信息，请参见[IPsec-VPN应用场景（绑定转发路由器）](https://www.alibabacloud.com/help/zh/vpn-gateway/latest/associate-ipsec-vpn-connections-with-transit-routers#concept-2247716)。 |

### SSL-VPN

SSL-VPN是一种基于OpenVPN架构的网络连接技术，适用于在互联网客户端与VPC之间建立网络连接。部署后，仅需要在互联网客户端中加载证书并发起连接，互联网客户端便可与VPC互通。

SSL-VPN仅支持绑定国际标准商用密码算法的公网VPN网关实例。SSL-VPN更多应用场景，请参见[SSL-VPN应用场景](https://www.alibabacloud.com/help/zh/vpn-gateway/latest/common-scenarios-of-ssl-vpn#concept-2248829)。

![产品简介-202209-2](https://help-static-aliyun-doc.aliyuncs.com/assets/img/zh-CN/1709152661/p487115.png)

## 产品优势

- 安全

  使用网络密钥交换IKE（Internet Key Exchange）和IP层协议安全结构IPsec（Internet Protocol Security）协议对传输数据进行加密，保证数据安全可信。

- 稳定

  底层采用双机热备架构，故障时秒级切换，保证会话不中断，业务不受影响。

- 简单

  功能开通即用，配置实时生效，实现快速部署。

- 低成本

  支持基于互联网建立加密隧道，相比使用物理专线成本更低。