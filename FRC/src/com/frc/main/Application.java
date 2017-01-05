package com.frc.main;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

import com.frc.core.common.DefaultValues;
import com.frc.loader.PropertiesLoader;
import com.frc.redis.migrate.MigrateThread;
import com.frc.thrift.stub.FrcService;
import com.frc.utility.FRCLogger;
import com.frc.zookeeper.client.ZkClient;

/**
 * @author songbin
 * 
 * 
 */
public class Application {
    static ZkClient          zkClient    = new ZkClient();

    private static FrcServer theInstance = new FrcServer();

    private static TServer   server      = null;

    private static String getClassName() {
        return "Application";
    }

    private static int thrift_port = PropertiesLoader.getIntance().getThriftListenPort();

    public static void main(String[] args) {
        System.out.println("hello world");
        zkClient.start(-100);
        new Thread(new MigrateThread()).start();
        serverStart();
    }

    private static boolean createThrift() {
        String flag = getClassName() + ".createThrift";
        try {
            TProcessor tProcessor = new FrcService.Processor<FrcService.Iface>(theInstance);

            TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(thrift_port);
            TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(tnbSocketTransport);
            tnbArgs.maxReadBufferBytes = DefaultValues.THRIFT_MAX_READ_BUF;
            tnbArgs.processor(tProcessor);
            // tnbArgs.transportFactory(new LCQTFramedTransport.Factory());
            tnbArgs.transportFactory(new TFramedTransport.Factory());
            tnbArgs.protocolFactory(new TBinaryProtocol.Factory());

            server = new TNonblockingServer(tnbArgs);
            // server.setServerEventHandler(new LCQTServerEventHandler());
            return true;
        } catch (Exception e) {
            FRCLogger.getInstance().warn(-100, flag, "exception", e);
            return false;
        }
    }

    private static boolean serverStart() {
        String flag = getClassName() + ".serverStart";
        try {
            if (!createThrift()) {
                System.exit(0);
            }

            FRCLogger.getInstance().warn(-100, flag, "thrift server success, port:{}", null,
                thrift_port);
            server.serve();

            return true;
        } catch (Exception e) {
            FRCLogger.getInstance().warn(-100, flag, "exception", e);
            return false;
        }

    }

}
