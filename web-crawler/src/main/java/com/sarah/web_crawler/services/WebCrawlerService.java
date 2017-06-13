package com.sarah.web_crawler.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sarah.web_crawler.constants.Constants;
import com.sarah.web_crawler.entities.UrlHistory;
import com.sarah.web_crawler.entities.UrlScrapJob;
import com.sarah.web_crawler.repositories.UrlScrapJobHistoryRepository;
import com.sarah.web_crawler.repositories.UrlScrapJobRepository;
import com.sarah.web_crawler.utils.ConfigUtils;
import com.sarah.web_crawler.utils.HDFSUtils;

/**
 * The interface WebCrawlerService
 * 
 * @author chandan
 */
@Service
public class WebCrawlerService implements Constants.JobStatus, Constants.Http {
	/**
	 * urlHistoryRepository
	 */
	@Autowired
	UrlScrapJobHistoryRepository urlHistoryRepository;
	/**
	 * urlScrapJobRepository
	 */
	@Autowired
	UrlScrapJobRepository urlScrapJobRepository;
	/**
	 * configUtils
	 */
	@Autowired
	ConfigUtils configUtils;
	/**
	 * hdfsUtils
	 */
	@Autowired
	HDFSUtils hdfsUtils;
	/**
	 * threadPool
	 */
	@Autowired
	ExecutorService threadPool;

	/**
	 * uploadFilesToHDFS
	 * 
	 * @param multipartFile
	 * @param urlScrapJob
	 * @throws Exception
	 */
	public void uploadFilesToHDFS(MultipartFile multipartFile, UrlScrapJob urlScrapJob) throws Exception {
		WebCrawlerTask webCrawlerTask = new WebCrawlerTask(multipartFile, urlScrapJob);
		threadPool.submit(webCrawlerTask);
	}

	/**
	 * startCrawling
	 * 
	 * @param urlToCrawl
	 * @param urlScrapJob
	 * @throws Exception
	 */
	public void startCrawling(String urlToCrawl, UrlScrapJob urlScrapJob) throws Exception {
		WebCrawlerTask webCrawlerTask = new WebCrawlerTask(urlToCrawl, urlScrapJob);
		threadPool.submit(webCrawlerTask);
	}

	/**
	 * The class WebCrawlerTask
	 */
	private class WebCrawlerTask implements Callable<Void> {
		/**
		 * urlToCrawl
		 */
		String urlToCrawl;
		/**
		 * rootUrlRaw
		 */
		String rootUrlRaw;
		/**
		 * rootProtocol
		 */
		String rootProtocol;
		/**
		 * rootDirectory
		 */
		String rootDirectory;

		/**
		 * completeRootDirectory
		 */
		String completeRootDirectory;

		/**
		 * urlScrapJob
		 */
		UrlScrapJob urlScrapJob;
		/**
		 * multipartFile
		 */
		MultipartFile multipartFile;

		/**
		 * isHDFSRootDirectorySaved
		 */
		boolean isHDFSRootDirectorySaved;

		int pageCount = 0;

		/**
		 * WebCrawlerTask
		 * 
		 * @param urlToCrawl
		 * @param urlScrapJob
		 * @throws Exception
		 */
		public WebCrawlerTask(String urlToCrawl, UrlScrapJob urlScrapJob) throws Exception {
			if (!urlToCrawl.contains("http"))
				urlToCrawl = "http://" + urlToCrawl;
			this.urlToCrawl = urlToCrawl;
			this.urlScrapJob = urlScrapJob;
			rootDirectory = getDenormalizedNameFromLink(urlToCrawl);
			completeRootDirectory = configUtils.getHdfsRootDir() + "/" + getDenormalizedNameFromLink(urlToCrawl);
			rootUrlRaw = getRootUrlWithoutHttp(urlToCrawl);
			setRootProtocol(urlToCrawl);
		}

		/**
		 * WebCrawlerTask
		 * 
		 * @param multipartFile
		 * @param urlScrapJob
		 * @throws Exception
		 */
		public WebCrawlerTask(MultipartFile multipartFile, UrlScrapJob urlScrapJob) throws Exception {
			this.urlScrapJob = urlScrapJob;
			this.multipartFile = multipartFile;
			rootDirectory = configUtils.getHdfsDirectoryUploadRawFile();
		}

		/**
		 * call
		 * 
		 * @throws Exception
		 */
		@Override
		public Void call() throws Exception {
			try {
				if (multipartFile == null) {
					processPage(urlToCrawl.replace("../", ""));
				} else {
					processFile(multipartFile);
				}
			} catch (Exception e) {
				updateJobStatus(COMPLETED);
			}
			return null;
		}

		/**
		 * processFile
		 * 
		 * @param multipartFile
		 * @throws Exception
		 */
		private void processFile(MultipartFile multipartFile) throws Exception {
			if (multipartFile != null) {
				try {
					if (multipartFile.getOriginalFilename().endsWith(".zip")) {
						hdfsUtils
								.writeToHDFSBinary("", urlScrapJob.getUniversity(), configUtils,
										new StringBuilder(configUtils.getHdfsRootDir()).append("/")
												.append(rootDirectory).append("/")
												.append(getDenormalizedNameFromLink(
														multipartFile.getOriginalFilename()))
												.toString(),
										multipartFile.getInputStream());
					} else {
						hdfsUtils
								.writeToHDFSBinary(0, "", urlScrapJob.getUniversity(),
										new StringBuilder(configUtils.getHdfsRootDir()).append("/")
												.append(rootDirectory).append("/")
												.append(getDenormalizedNameFromLink(
														multipartFile.getOriginalFilename()))
												.toString(),
										multipartFile.getInputStream(), "");
					}
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
		}

		/**
		 * processPage
		 * 
		 * @param urlToCrawl
		 */
		private void processPage(String urlToCrawl) {

			threadPool.submit(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					try {
						if (urlToCrawl != null && !urlToCrawl.trim().equals("")) {
							Future<List<UrlHistory>> urlHistoryListFuture = urlHistoryRepository.findByUrl(urlToCrawl);
							while (!urlHistoryListFuture.isDone()) {
							}
							List<UrlHistory> urlHistoryList = urlHistoryListFuture.get();
							if (urlHistoryList == null || urlHistoryList.size() == 0) {
								pageCount++;
								try {
									updateJobStatus(RUNNING);
								} catch (Exception e) {
								}
								UrlHistory urlHistory = new UrlHistory(urlToCrawl, urlScrapJob.getId(), new Date());
								urlHistory.setUrlTrackStatus(RUNNING);
								urlHistory = urlHistoryRepository.save(urlHistory);
								checkSublinks(extractHTML(urlToCrawl, urlHistory));
								updateJobStatus(COMPLETED);
							}
						}
					} catch (ConnectException | UnknownHostException e) {
						updateJobStatus(COMPLETED);
					} catch (Exception e) {
						updateJobStatus(COMPLETED);
					}
					return null;
				}
			});

		}

		/**
		 * checkSublinks
		 * 
		 * @param doc
		 */
		private void checkSublinks(Document doc) {
			if (doc == null)
				return;
			Elements questions = doc.select("a[href]");

			for (Element link : questions) {
				String href = link.attr("href");
				if ((href.contains("http") && href.contains(rootUrlRaw)) || !href.contains("http")) {
					try {
						processPage(link.attr("href").replace("../", ""));
					} catch (Exception e) {
						continue;
					}
				}
			}
		}

		/**
		 * httpURLConnection
		 * 
		 * @param urlToCrawl
		 * @return Object[]
		 * @throws Exception
		 */
		public Object[] httpURLConnection(String urlToCrawl) throws Exception {

			if (!urlToCrawl.contains("http")) {
				urlToCrawl = new StringBuilder(rootProtocol).append(rootUrlRaw).append("/").append(urlToCrawl)
						.toString();
			}

			URL url = new URL(urlToCrawl);
			Proxy proxy = null;
			if (configUtils.getProxyEnabled()) {
				proxy = new Proxy(Proxy.Type.HTTP,
						new InetSocketAddress(configUtils.getProxyServerHostname(), configUtils.getProxyServerPort()));
			}
			if (urlToCrawl.contains("https")) {
				try {
					HttpsURLConnection httpsURLConnection = proxy != null
							? (HttpsURLConnection) url.openConnection(proxy)
							: (HttpsURLConnection) url.openConnection();
					httpsURLConnection.setConnectTimeout(configUtils.getWebcrawlerHttpTimeout());
					httpsURLConnection.setReadTimeout(configUtils.getWebcrawlerHttpTimeout());
					httpsURLConnection.setRequestProperty("User-Agent", USER_AGENT);

					String redirect = httpsURLConnection.getHeaderField("Location");
					if (redirect != null) {
						processPage(redirect);
						return null;
					}
					return new Object[] { httpsURLConnection.getInputStream(), httpsURLConnection.getContentType() };
				} catch (Exception e2) {
					throw new Exception(e2.getMessage());
				}
			} else {
				try {
					HttpURLConnection httpURLConnection = proxy != null ? (HttpURLConnection) url.openConnection(proxy)
							: (HttpURLConnection) url.openConnection();
					httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
					httpURLConnection.setConnectTimeout(configUtils.getWebcrawlerHttpTimeout());
					httpURLConnection.setReadTimeout(configUtils.getWebcrawlerHttpTimeout());

					String redirect = httpURLConnection.getHeaderField("Location");
					if (redirect != null) {
						processPage(redirect);
						return null;
					}
					return new Object[] { httpURLConnection.getInputStream(), httpURLConnection.getContentType() };
				} catch (Exception e2) {
					throw new Exception(e2.getMessage());
				}
			}
		}

		/**
		 * extractHTML
		 * 
		 * @param urlToCrawl
		 * @param urlHistory
		 * @return Document
		 * @throws Exception
		 */
		private Document extractHTML(String urlToCrawl, UrlHistory urlHistory) throws Exception {

			if (!urlToCrawl.contains("http")) {
				urlToCrawl = new StringBuilder(rootProtocol).append(rootUrlRaw).append("/").append(urlToCrawl)
						.toString();
			}
			Object[] httpUrlConnectionResult = httpURLConnection(urlToCrawl);
			if (httpUrlConnectionResult == null)
				return null;
			InputStream inputStream = (InputStream) httpUrlConnectionResult[0];
			String contentType = (String) httpUrlConnectionResult[1];
			String hdfsFilePath = new StringBuilder(configUtils.getHdfsRootDir()).append("/").append(rootDirectory)
					.append("/").append(getDenormalizedNameFromLink(urlToCrawl)).toString();
			if (contentType == null || !contentType.toLowerCase().contains("text/html")) {
				if (hdfsUtils.writeToHDFSBinary(pageCount, urlToCrawl, urlScrapJob.getUniversity(), hdfsFilePath,
						inputStream, urlHistory.getId())) {
					urlHistory.setUrlTrackStatus(COMPLETED);
					urlHistory.setHdfsFilePath(hdfsFilePath);
					urlHistoryRepository.save(urlHistory);
					updateJobStatus(COMPLETED);
					saveHDFSRootDirectory();
				}
			}

			else {
				return saveHtmlPage(inputStream, urlToCrawl, contentType, urlHistory);
			}
			return null;
		}

		/**
		 * saveHtmlPage
		 * 
		 * @param inputStream
		 * @param urlToCrawl
		 * @param contentType
		 * @param urlHistory
		 * @return Document
		 * @throws Exception
		 */
		void saveHDFSRootDirectory() throws Exception {
			if (!isHDFSRootDirectorySaved) {
				urlScrapJob.setHdfsDirectoryPath(completeRootDirectory);
				urlScrapJobRepository.save(urlScrapJob);
				isHDFSRootDirectorySaved = true;
			}
		}

		/**
		 * saveHtmlPage
		 * 
		 * @param inputStream
		 * @param urlToCrawl
		 * @param contentType
		 * @param urlHistory
		 * @return Document
		 * @throws Exception
		 */
		private Document saveHtmlPage(InputStream inputStream, String urlToCrawl, String contentType,
				UrlHistory urlHistory) throws Exception {
			if (checkUrlHavingBinaryContent(urlToCrawl)) {
				String hdfsFilePath = new StringBuilder(configUtils.getHdfsRootDir()).append("/").append(rootDirectory)
						.append("/").append(getDenormalizedNameFromLink(urlToCrawl)).toString();
				if (hdfsUtils.writeToHDFSBinary(pageCount, urlToCrawl, urlScrapJob.getUniversity(), hdfsFilePath,
						inputStream, urlHistory.getId())) {
					urlHistory.setHdfsFilePath(hdfsFilePath);
					urlHistory.setUrlTrackStatus(COMPLETED);
					urlHistoryRepository.save(urlHistory);
					updateJobStatus(COMPLETED);
					saveHDFSRootDirectory();
					return null;
				}
			}
			String tempLine = null;
			StringBuffer htmlData = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			while ((tempLine = in.readLine()) != null) {
				htmlData.append(tempLine);
			}
			
			Document htmldoc = Jsoup.parse(String.valueOf(htmlData));
			Document doc = Jsoup.parse(String.valueOf(htmlData));
			
			System.out.println("JSoup Data  "+doc.html());
			// checking the keywords
			if (!urlToCrawl.contains("#")) {
				doc.outputSettings(new Document.OutputSettings().prettyPrint(false));
				// makes html() preserve linebreaks and spacing
				doc.select("br").append("\\n");
				doc.select("p").prepend("\\n\\n");
				
				String s = doc.html().replaceAll("\\\\n", "\n");
				System.out.println("JSoup Cleaned Data  "+s);
				String text = (Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false)));
				String[] lines = text.split("\n");
				StringBuilder cleanedText = new StringBuilder();
				
				for (String line : lines) {
					if (!line.trim().equals("")) {
						cleanedText.append(line).append("\n");
					}
				}
				System.out.println("JSoup Cleaned Text  "+cleanedText);
				if (!cleanedText.toString().equals("")) {
					String hdfsFilePath = new StringBuilder(configUtils.getHdfsRootDir()).append("/")
							.append(rootDirectory).append("/").append(getDenormalizedNameFromLink(urlToCrawl))
							.toString();
					// System.out.println("Cleaned Text "+cleanedText);
					if (hdfsUtils.writeToHDFS(pageCount, urlToCrawl, urlScrapJob.getUniversity(), hdfsFilePath,
							cleanedText.toString(), urlHistory.getId())) {
						urlHistory.setHdfsFilePath(hdfsFilePath);
						urlHistory.setUrlTrackStatus(COMPLETED);
						urlHistoryRepository.save(urlHistory);
						updateJobStatus(COMPLETED);
						saveHDFSRootDirectory();
					}
				}

			}
			return htmldoc;
		}

		/**
		 * checkUrlHavingBinaryContent
		 * 
		 * @param urlToCrawl
		 * @return boolean
		 * @throws Exception
		 */
		private boolean checkUrlHavingBinaryContent(String urlToCrawl) throws Exception {
			if (urlToCrawl.endsWith(".pdf") || urlToCrawl.endsWith(".doc") || urlToCrawl.endsWith(".txt")) {
				return true;
			}
			return false;
		}

		/**
		 * getRootUrlWithoutHttp
		 * 
		 * @param url
		 * @return String
		 * @throws Exception
		 */
		private String getRootUrlWithoutHttp(String url) throws Exception {
			url = url.trim();
			url = url.contains("https") ? url.substring(8) : url.contains("http") ? url.substring(7) : url;
			try {
				url = url.substring(0, url.indexOf("/"));
			} catch (Exception e) {
				url = url.substring(0, url.length());
			}
			String urlParts[] = url.split("\\.");
			if (urlParts.length > 2) {
				return new StringBuilder(urlParts[1]).append(".").append(urlParts[2]).toString();
			} else {
				return url;
			}
		}

		/**
		 * setRootProtocol
		 * 
		 * @param url
		 * @throws Exception
		 */
		private void setRootProtocol(String url) throws Exception {
			url = url.trim();
			rootProtocol = url.contains("https") ? "https://" : "http://";
		}

		/**
		 * getDenormalizedNameFromLink
		 * 
		 * @param url
		 * @return String
		 * @throws Exception
		 */
		private String getDenormalizedNameFromLink(String url) throws Exception {
			url = url.contains("https") ? url.substring(8) : url.contains("http") ? url.substring(7) : url;
			return url.replace("/", "_");
		}

		/**
		 * updateJobStatus
		 * 
		 * @param jobStatus
		 * @throws Exception
		 */
		private void updateJobStatus(String jobStatus) throws Exception {
			urlScrapJob.setTaskStatus(jobStatus);
			if (jobStatus.equals(COMPLETED)) {
				urlScrapJob.setDateCompleted(new Date());
			}
			urlScrapJobRepository.save(urlScrapJob);
		}

	}

}
