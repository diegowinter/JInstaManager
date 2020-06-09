package jinstamanager.util;

import java.io.File;
import java.io.FileOutputStream;

import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

public class GenerateReportPdf {

	//private static String FILE = "res/FirstPdf3.pdf";
	private static Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
	private static Font subtitleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
	private static Font smallNormalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
	private static Font smallBoldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

	public static void generateReport(File file, java.util.List<InstagramUserSummary> followers,
			java.util.List<InstagramUserSummary> following,
			java.util.List<InstagramUserSummary> notFollowingBack,
			java.util.List<InstagramUserSummary> notFollowBack,
			String username)
	{
		try {
			Document document = new Document(PageSize.A4, 40, 20, 50, 50);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);

			document.open();
			addMetaData(document);
			addTitlePage(document, username);
			addContent(document, followers, following, notFollowingBack, notFollowBack, username);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addMetaData(Document document) {
		document.addTitle("Relatório JInstaManager");
		document.addAuthor("JInstaManager 0.2.0");
	}

	private static void addTitlePage(Document document, String username) throws DocumentException {
		Paragraph header = new Paragraph();

		Paragraph title = new Paragraph("JInstaManager 0.2.0", titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		header.add(title);

		Paragraph subtitle = new Paragraph("Relatório detalhado de seguidores no Instagram", subtitleFont);
		subtitle.setAlignment(Element.ALIGN_CENTER);
		header.add(subtitle);

		Paragraph user = new Paragraph("@" + username, smallNormalFont);
		user.setAlignment(Element.ALIGN_CENTER);
		header.add(user);

		addEmptyLine(header, 2);

		document.add(header);
	}

	private static void addContent(Document document,
			java.util.List<InstagramUserSummary> followers,
			java.util.List<InstagramUserSummary> following,
			java.util.List<InstagramUserSummary> notFollowingBack,
			java.util.List<InstagramUserSummary> notFollowBack,
			String username) throws DocumentException
	{
		Paragraph followersSection = new Paragraph();
		
		// Followers Section
		Paragraph title = new Paragraph("Seguidores", subtitleFont);
		followersSection.add(title);
		followersSection.add(new Paragraph("Os seguintes usuários seguem @" + username + " no Instagram."));
		addEmptyLine(followersSection, 1);
		createListOfUsers(followersSection, followers);
		addEmptyLine(followersSection, 2);
		document.add(followersSection);
		
		// Following Section
		Paragraph followingSection = new Paragraph();
		title = new Paragraph("Seguindo", subtitleFont);
		followingSection.add(title);
		followingSection.add(new Paragraph("@" + username + " segue os seguintes usuários no Instagram."));
		addEmptyLine(followingSection, 1);
		createListOfUsers(followingSection, following);
		addEmptyLine(followingSection, 2);
		document.add(followingSection);
		
		// Not Following Back Section
		Paragraph notFollowingBackSection = new Paragraph();
		title = new Paragraph("Não seguem de volta", subtitleFont);
		notFollowingBackSection.add(title);
		notFollowingBackSection.add(new Paragraph("Os seguintes usuários não seguem @" + username + " de volta no Instagram."));
		addEmptyLine(notFollowingBackSection, 1);
		createListOfUsers(notFollowingBackSection, notFollowingBack);
		addEmptyLine(notFollowingBackSection, 2);
		document.add(notFollowingBackSection);
		
		// Not Follow Back Section
		Paragraph notFollowBackSection = new Paragraph();
		title = new Paragraph("Não sigo de volta", subtitleFont);
		notFollowBackSection.add(title);
		notFollowBackSection.add(new Paragraph("@" + username + " não segue de volta os seguintes usuários no Instagram."));
		addEmptyLine(notFollowBackSection, 1);
		createListOfUsers(notFollowBackSection, notFollowBack);
		addEmptyLine(notFollowBackSection, 2);
		document.add(notFollowBackSection);
	}

	private static void createListOfUsers(Paragraph paragraph, java.util.List<InstagramUserSummary> users)
			throws BadElementException {

		Paragraph item;

		for (InstagramUserSummary user : users) {
			item = new Paragraph();
			item.add(new Phrase("@" + user.getUsername(), smallNormalFont));
			item.add(new Phrase(" " + user.getFull_name(), smallBoldFont));
			paragraph.add(item);
		}
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}
