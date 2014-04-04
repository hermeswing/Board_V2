package util.mysql;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

public class SecurityUtil {

	/**
	 * RFC 1321에 규정되어 있는 MD5() 구현
	 * 
	 * @param inpara
	 * @return
	 */
	public static String md5(String inpara) {
		byte[] bpara = new byte[inpara.length()];
		byte[] rethash;
		int i;
		for (i = 0; i < inpara.length(); i++)
			bpara[i] = (byte) (inpara.charAt(i) & 0xff);
		try {
			MessageDigest md5er = MessageDigest.getInstance("MD5");
			rethash = md5er.digest(bpara);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
		StringBuffer r = new StringBuffer(32);
		for (i = 0; i < rethash.length; i++) {
			String x = Integer.toHexString(rethash[i] & 0xff).toUpperCase();
			if (x.length() < 2)
				r.append("0");
			r.append(x);
		}
		return r.toString();
	}

	/**
	 * 표준인 보안해시 알고리즘 SHA1을 사용하여 MySQL의 PASSWORD 함수 구현
	 * 자바 내장 함수인 MessageDigest를 사용하여 입력된 스트링에 SHA1 알고리즘을 적용하고 그 결과에 다시 한번
	 * SHA1 알고리즘을 적용 한다. 생성된 값을 16진수 문자열로 변환하여 맨처음에 "*" 문자를 붙여 41자리 스트링으로 돌려준다.
	 * 함수의 입출력은 모두 스트링이다.
	 * 
	 * @param inpara
	 * @return
	 */
	public static String password(String inpara) {
		byte[] bpara = new byte[inpara.length()];

		byte[] rethash;
		int i;
		for (i = 0; i < inpara.length(); i++)
			bpara[i] = (byte) (inpara.charAt(i) & 0xff);
		try {
			MessageDigest sha1er = MessageDigest.getInstance("SHA1");
			rethash = sha1er.digest(bpara); // stage1
			rethash = sha1er.digest(rethash); // stage2
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
		StringBuffer r = new StringBuffer(41);
		r.append("*");
		for (i = 0; i < rethash.length; i++) {
			String x = Integer.toHexString(rethash[i] & 0xff).toUpperCase();
			if (x.length() < 2)
				r.append("0");
			r.append(x);
		}
		return r.toString();
	}

	/**
	 * MySQL의 OLD_PASSWORD 함수 구현
	 * @param inpara
	 * @return
	 */
	public static String oldPassword(String inpara) {
		byte[] bpara = new byte[inpara.length()];
		long lvar1 = 1345345333;
		long ladd = 7;
		long lvar2 = 0x12345671;
		int i;
		if (inpara.length() <= 0)
			return "";
		for (i = 0; i < inpara.length(); i++)
			bpara[i] = (byte) (inpara.charAt(i) & 0xff);
		for (i = 0; i < inpara.length(); i++) {
			if (bpara[i] == ' ' || bpara[i] == '\t')
				continue;
			lvar1 ^= (((lvar1 & 63) + ladd) * bpara[i]) + (lvar1 << 8);
			lvar2 += (lvar2 << 8) ^ lvar1;

			ladd += bpara[i];
		}
		lvar1 = lvar1 & 0x7fffffff;
		lvar2 = lvar2 & 0x7fffffff;
		StringBuffer r = new StringBuffer(16);
		String x = Long.toHexString(lvar1);
		for (i = 8; i > x.length(); i--)
			r.append("0");
		r.append(x);
		x = Long.toHexString(lvar2);
		for (i = 8; i > x.length(); i--)
			r.append("0");
		r.append(x);
		return r.toString();
	}
}
