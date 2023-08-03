package com.practice.design.oss;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.practice.design.oss.entity.SysOssVo;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * 创建Ossclient
 */
public class OssClient {


    private final String configKey;

    private final OssProperties properties;

    private final AmazonS3 client;

    /**
     * 云服务商
     */
    String[] CLOUD_SERVICE = new String[] {"aliyun", "qcloud", "qiniu", "obs"};

    public OssClient(String configKey, OssProperties ossProperties) {
        this.configKey = configKey;
        this.properties = ossProperties;
        try {
            AwsClientBuilder.EndpointConfiguration endpointConfig =
                    new AwsClientBuilder.EndpointConfiguration(properties.getEndpoint(), properties.getRegion());

            AWSCredentials credentials = new BasicAWSCredentials(properties.getAccessKey(), properties.getSecretKey());
            AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
            ClientConfiguration clientConfig = new ClientConfiguration();
            if ("Y".equals(properties.getIsHttps())) {
                clientConfig.setProtocol(Protocol.HTTPS);
            } else {
                clientConfig.setProtocol(Protocol.HTTP);
            }
            AmazonS3ClientBuilder build = AmazonS3Client.builder()
                    .withEndpointConfiguration(endpointConfig)
                    .withClientConfiguration(clientConfig)
                    .withCredentials(credentialsProvider)
                    .disableChunkedEncoding();
            if (!StringUtils.containsAny(properties.getEndpoint(), CLOUD_SERVICE)) {
                // minio 使用https限制使用域名访问 需要此配置 站点填域名
                build.enablePathStyleAccess();
            }
            this.client = build.build();

            createBucket();
        } catch (Exception e) {
            if (e instanceof OssException) {
                throw e;
            }
            throw new OssException("配置错误! 请检查系统配置:[" + e.getMessage() + "]");
        }
    }

    public void createBucket() {
        try {
            String bucketName = properties.getBucketName();
            if (client.doesBucketExistV2(bucketName)) {
                return;
            }
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
           // AccessPolicyType accessPolicy = getAccessPolicy();
       //     createBucketRequest.setCannedAcl(accessPolicy.getAcl());
            client.createBucket(createBucketRequest);
        //    client.setBucketPolicy(bucketName, getPolicy(bucketName, accessPolicy.getPolicyType()));
        } catch (Exception e) {
            throw new OssException("创建Bucket失败, 请核对配置信息:[" + e.getMessage() + "]");
        }
    }

    /**
     * 进行文件上传操作
     */
    public SysOssVo upload(File file) {
        SysOssVo sysOssVo = new SysOssVo();
        String originalfileName = file.getName();
        String suffix = StringUtils.substring(originalfileName, originalfileName.lastIndexOf("."), originalfileName.length());
        OssClient storage = OssFactory.instance();
        UploadResult uploadResult = storage.uploadSuffix(file, suffix);
        // 保存文件信息
        return sysOssVo;
    }

    // 进行文件上传
    private UploadResult uploadSuffix(File file, String suffix) {
        return null;
    }


    public boolean checkPropertiesSame(OssProperties properties) {
        return this.properties.equals(properties);
    }
}
