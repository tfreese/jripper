/**
 * Created: 07.10.2013
 */

package de.freese.jripper.core;

/**
 * Klasse mit der Konfiguation.
 * 
 * @author Thomas Freese
 */
public final class Settings
{
	/**
	 * 
	 */
	private static final Settings INSTANCE = new Settings();

	/**
	 * @return {@link Settings}
	 */
	public static Settings getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Laufwerk
	 */
	private String device = null;

	/**
	 * 
	 */
	private int framesPerSecond = 75;

	/**
	 * 
	 */
	private int mp3Bitrate = 320;

	/**
	 * Arbeitsverzeichnis
	 */
	private String workDir = null;

	/**
	 * Erstellt ein neues {@link Settings} Object.
	 */
	private Settings()
	{
		super();

		// setWorkDir(System.getProperty("java.io.tmpdir"));
		// setWorkDir(System.getProperty("user.dir"));
		setWorkDir(System.getProperty("user.home") + "/.jripper");

		setMp3Bitrate(320);
		setDevice(JRipperUtils.detectCDDVD());
		setFramesPerSecond(75);
	}

	/**
	 * @return String
	 */
	public String getDevice()
	{
		return this.device;
	}

	/**
	 * @return int
	 */
	public int getFramesPerSecond()
	{
		return this.framesPerSecond;
	}

	/**
	 * @return int
	 */
	public int getMp3Bitrate()
	{
		return this.mp3Bitrate;
	}

	/**
	 * Liefert das Arbeitsverzeichnis.
	 * 
	 * @return String
	 */
	public String getWorkDir()
	{
		return this.workDir;
	}

	/**
	 * @param device String
	 */
	public void setDevice(final String device)
	{
		this.device = device;
	}

	/**
	 * @param framesPerSecond int
	 */
	public void setFramesPerSecond(final int framesPerSecond)
	{
		this.framesPerSecond = framesPerSecond;
	}

	/**
	 * @param mp3Bitrate int
	 */
	public void setMp3Bitrate(final int mp3Bitrate)
	{
		this.mp3Bitrate = mp3Bitrate;
	}

	/**
	 * Setzt das Arbeitsverzeichnis.
	 * 
	 * @param workDir String
	 */
	public void setWorkDir(final String workDir)
	{
		this.workDir = workDir;
	}
}
