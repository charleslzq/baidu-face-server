package com.github.charleslzq.baiduface.client

import com.baidu.aip.face.AipFace
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(
        prefix = "charleslzq.baidu.aipface.client"
)
open class AipFaceClientProperties {
    /**
     * appId, get it from baidu ai official website
     */
    var appId: String? = null

    /**
     * apiKey, get it from baidu ai official website
     */
    var apiKey: String? = null

    /**
     * secretKey, get it from baidu ai official website
     */
    var secretKey: String? = null

    /**
     * Time out for connection
     */
    var connectionTimeout: Int = 2000

    /**
     * Time out for socket
     */
    var socketTimeout: Int = 60000

    /**
     * setup proxy
     */
    var proxy: Proxy = Proxy()
}

enum class ProxyType {
    NONE,
    HTTP,
    SOCKET
}

class Proxy {
    /**
     * specify the type of proxy, can be none/http/socket
     */
    var type: ProxyType = ProxyType.NONE

    /**
     * proxy host
     */
    var host: String? = null

    /**
     * proxy port
     */
    var port: Int = -1
}

@Configuration
@EnableConfigurationProperties(AipFaceClientProperties::class)
@ConditionalOnProperty(
        name = ["charleslzq.baidu.aipface.client.autoImport"],
        matchIfMissing = true
)
open class AipFaceClientConfiguration {

    @Autowired
    lateinit var aipFaceClientProperties: AipFaceClientProperties

    @Bean
    open fun baiduAipFace() = aipFaceClientProperties.let {
        it.checkAndCreateAipFace().apply {
            setConnectionTimeoutInMillis(it.connectionTimeout)
            setSocketTimeoutInMillis(it.socketTimeout)

            when (it.proxy.type) {
                ProxyType.NONE -> {
                }
                ProxyType.HTTP -> {
                    it.checkProxySetting()
                    setHttpProxy(it.proxy.host, it.proxy.port)
                }
                ProxyType.SOCKET -> {
                    it.checkProxySetting()
                    setSocketProxy(it.proxy.host, it.proxy.port)
                }
            }
        }
    }

    @Bean
    @ConditionalOnMissingBean(name = ["gsonForAipFace"])
    open fun gsonForAipFace() = Gson()

    @Bean
    open fun baiduFaceApi(
            baiduAipFace: AipFace,
            @Qualifier("gsonForAipFace") gson: Gson
    ): BaiduFaceApi {
        return BaiduFaceApiAdapter(baiduAipFace, gson)
    }

    private fun AipFaceClientProperties.checkAndCreateAipFace(): AipFace {
        if (appId.isNullOrBlank() || apiKey.isNullOrBlank() || secretKey.isNullOrBlank()) {
            throw IllegalArgumentException("App Id/Api Key/Secret Key required")
        }
        return AipFace(appId, apiKey, secretKey)
    }

    private fun AipFaceClientProperties.checkProxySetting() {
        if (proxy.host.isNullOrBlank() || proxy.port <= 0) {
            throw IllegalArgumentException("Illegal Proxy Setting!")
        }
    }
}