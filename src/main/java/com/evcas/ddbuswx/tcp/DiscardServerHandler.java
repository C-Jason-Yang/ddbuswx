package com.evcas.ddbuswx.tcp;

import com.evcas.ddbuswx.common.utils.JsonTools;
import com.evcas.ddbuswx.entity.RTBusArriveLeave;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.log4j.Log4j2;

import java.net.InetSocketAddress;

/**
 * @author noxn
 * @date 2018/8/15
 */
@Log4j2
public class DiscardServerHandler extends ChannelHandlerAdapter {

    private static ChannelGroup ysChannelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 这里覆盖了chanelRead()事件处理方法。 每当从客户端收到新的数据时， 这个方法会在收到消息时被调用，
     * 这个例子中，收到的消息的类型是ByteBuf
     *
     * @param ctx 通道处理的上下文信息
     * @param msg 接收的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
            String clientIp = insocket.getAddress().getHostAddress();
            log.info(clientIp);
            ByteBuf in = (ByteBuf) msg;
            // 打印客户端输入，传输过来的的字符
            log.info(in.toString(CharsetUtil.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /*
             * ByteBuf是一个引用计数对象，这个对象必须显示地调用release()方法来释放。
             * 请记住处理器的职责是释放所有传递到处理器的引用计数对象。
             */
            // 抛弃收到的数据
            ReferenceCountUtil.release(msg);
        }
    }

    public static void send(RTBusArriveLeave rtBusArriveLeave) {
        ByteBuf buf;
        try {
            buf = Unpooled.buffer(JsonTools.gson.toJson(rtBusArriveLeave).getBytes().length);
            buf.writeBytes(JsonTools.gson.toJson(rtBusArriveLeave).getBytes());
            ysChannelGroup.writeAndFlush(buf);
            log.info(ysChannelGroup.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String ipAddress = ctx.channel().remoteAddress().toString();
        log.info(ipAddress);
        Object[] channelArray = ysChannelGroup.toArray();
        for (Object o : channelArray) {
            Channel channel = (Channel) o;
            String tempIpAddress = channel.remoteAddress().toString();
            if (":".split(tempIpAddress)[0].equals(":".split(ipAddress)[0])) {
                channel.close();
            }
        }
        ysChannelGroup.add(ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
