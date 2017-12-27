package com.znph.core.common.utlis;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
* @author Minco
* @date 2017年7月28日 下午4:13:05
* 
*/
public class GenerateQRCodeUtils {
	
	private static final int QRCOLOR = 0xFF000000;   //默认是黑色
	private static final int BGWHITE = 0xFFFFFFFF;   //背景颜色


	public static void main(String[] args) throws WriterException {
		try {
			String logoPath = "F:/T1dQ0FXihbXXaCwpjX.png";
			String image = getLogoQRCode("https://qr.alipay.com/bax07876gjtvxcxepgxc2092", logoPath);
			System.out.println(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成带logo的二维码图片
	 *
	 * @param qrUrl         二维码包含的url内容
	 * @param logoPath      logo的图片路径,没有logo用null传入
	 *
	 * @return Base64 PNG
	 */
	public static String getLogoQRCode(String qrUrl, String logoPath) {
		String content = qrUrl;
		try {
			GenerateQRCodeUtils zp = new GenerateQRCodeUtils();
			BufferedImage bim = zp.getQR_CODEBufferedImage(content, BarcodeFormat.QR_CODE, 400, 400, zp.getDecodeHintType());
			return zp.addLogo_QRCode(bim, logoPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 给二维码图片添加Logo
	 *
	 * @param bim
	 * @param logoPath
	 *
	 * @return String Base64 PNG
	 */
	public String addLogo_QRCode(BufferedImage bim, String logoPath) {
		try {
			/**
			 * 读取二维码图片，并构建绘图对象
			 */
			BufferedImage image = bim;

			if (logoPath != null && !logoPath.equals("")) {
				Graphics2D g = image.createGraphics();

				/**
				 * 读取Logo图片
				 */
				BufferedImage logo = ImageIO.read(new File(logoPath));

				/**
				 * 设置logo的大小,这里设置为二维码图片的1/6,太大会盖掉二维码
				 */
				int widthLogo = logo.getWidth(null) > image.getWidth() * 1 / 8 ? (image.getWidth() * 1 / 8) : logo.getWidth(null);

				int heightLogo = logo.getHeight(null) > image.getHeight() * 1 / 8 ? (image.getHeight() * 1 / 8) : logo.getWidth(null);

				/**
				 * logo放在中心
				 */
				int x = (image.getWidth() - widthLogo) / 2;
				int y = (image.getHeight() - heightLogo) / 2;

				/**
				 * logo放在右下角
				 *  int x = (image.getWidth() - widthLogo);
				 *  int y = (image.getHeight() - heightLogo);
				 */

				//开始绘制图片
				g.drawImage(logo, x, y, widthLogo, heightLogo, null);
				g.dispose();

				logo.flush();
			}


			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			ImageIO.write(image, "png", baos);
			/*
			 * 本地测试时用
				//二维码生成的路径，但是实际项目中，我们是把这生成的二维码显示到界面上的，因此下面的折行代码可以注释掉
				//可以看到这个方法最终返回的是这个二维码的imageBase64字符串
				//前端用 <img src="data:image/png;base64,${imageBase64QRCode}"/>  其中${imageBase64QRCode}对应二维码的imageBase64字符串
				String aString =  Thread.currentThread().getContextClassLoader().getResource("").getPath();
				String qrcodePicPath = "F:/qrcodetemp" + Random.class.newInstance().nextInt(100000) +".png";
	
				ImageIO.write(image, "png", new File(qrcodePicPath)); 
			 */			
			String imageBase64QRCode =  Base64.encodeBase64String(baos.toByteArray());
			bim.flush();
			image.flush();
			baos.flush();
			baos.close();
			return imageBase64QRCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成二维码bufferedImage图片
	 *
	 * @param content       编码内容
	 * @param barcodeFormat 编码类型
	 * @param width         图片宽度
	 * @param height        图片高度
	 * @param hints         设置参数
	 * @return
	 */
	public BufferedImage getQR_CODEBufferedImage(String content, BarcodeFormat barcodeFormat, int width, int height, Map<EncodeHintType, ?> hints) {
		MultiFormatWriter multiFormatWriter = null;
		BitMatrix bm = null;
		BufferedImage image = null;
		try {
			multiFormatWriter = new MultiFormatWriter();
			// 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
			bm = multiFormatWriter.encode(content, barcodeFormat, width, height, hints);
			int w = bm.getWidth();
			int h = bm.getHeight();
			image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

			// 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
				}
			}
		} catch (WriterException e) {
			e.printStackTrace();
		}

		image.flush();

		return image;
	}

	/**
	 * 设置二维码的格式参数
	 *
	 * @return
	 */
	public Map<EncodeHintType, Object> getDecodeHintType() {
		// 用于设置QR二维码参数
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 设置编码方式
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MARGIN, 0);
		hints.put(EncodeHintType.MAX_SIZE, 350);
		hints.put(EncodeHintType.MIN_SIZE, 100);

		return hints;
	}

}
