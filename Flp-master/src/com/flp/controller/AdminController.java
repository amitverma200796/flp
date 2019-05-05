package com.flp.controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.flp.annotations.LoginAuthentication;
import com.flp.model.Answer;
import com.flp.model.Board;
import com.flp.model.Chapter;
import com.flp.model.Grade;
import com.flp.model.LearnObjective;
import com.flp.model.ModuleTopic;
import com.flp.model.ModuleType;
import com.flp.model.Question;
import com.flp.model.QuestionComments;
import com.flp.model.QuestionTopic;
import com.flp.model.QuestionType;
import com.flp.model.School;
import com.flp.model.SchoolSession;
import com.flp.model.Section;
import com.flp.model.Session;
import com.flp.model.Subject;
import com.flp.model.SubjectSection;
import com.flp.model.Topic;
import com.flp.model.User;
import com.flp.model.UserForm;
import com.flp.service.AdminService;
import com.flp.service.SchoolTeacherService;
import com.flp.service.StudentCommonService;
import com.flp.service.UserService;
import com.flp.util.Utility;
import com.flp.vo.GlobalVO;
/**
 * 
 * @author Amresh
 *
 */
@Controller
@PropertySource("classpath:application.properties")
public class AdminController
{
	
	@Resource
	private Environment env;
	@Autowired
	AdminService adminService;

	@Autowired
	StudentCommonService studentCommonService;
	@Autowired
	UserService userService;
	@Autowired
	SchoolTeacherService schoolTeacherService;
	
	/**
	 * 
	 * @param topicId
	 * @param request
	 * @param response
	 * @return
	 */

	 	@LoginAuthentication
	    @RequestMapping("/admin/questionDataPageStandard/{topicId}.htm")
	    public ModelAndView questionDataPageStandardStan(@PathVariable(value="topicId") Long topicId/*,@PathVariable(value="loid") Long loid*/, HttpServletRequest request, HttpServletResponse response)
	    {
	       
	        ModelAndView mav = new ModelAndView("admin/tocStandardQuestionDataForStandardToc");
	       
	       
	        mav.addObject("questionTopic", schoolTeacherService.getAllEnableDisableQuestionsByTopic(topicId,0l));
	       
	        String propertyDirectoryString = Utility.getValueFromPropertyFile("flp.qa.images.retrieve");
	        String questionResourceDirectory = (propertyDirectoryString.charAt(propertyDirectoryString.length()-1) != '/' ? propertyDirectoryString+"/" : propertyDirectoryString);        //"D:/FLP_RESOURCES/QA_IMAGES";
	       
	        GlobalVO globalVO = new GlobalVO();
	        globalVO.setQuestionTypeList(schoolTeacherService.getQuestionType(QuestionType.QUESTION_TYPE, QuestionType.STATUS_ACTIVE));
	        globalVO.setQuestionDifficultyLevelList(schoolTeacherService.getQuestionType(QuestionType.QUESTION_DIFFICULTY, QuestionType.STATUS_ACTIVE));
	        globalVO.setQuestionCognitiveLevelList(schoolTeacherService.getQuestionType(QuestionType.QUESTION_COGNITIVE, QuestionType.STATUS_ACTIVE));
	       
	       
	        globalVO.setZoneList(schoolTeacherService.getQuestionType(QuestionType.QUESTION_ZONE, QuestionType.STATUS_ACTIVE));
	        //globalVO.setLoId(loid);
	        globalVO.setTopicId(topicId);
	        globalVO.setLoginUserType(User.USERTYPE_ADMIN);
	        globalVO.setQuestionResourceDirectory(questionResourceDirectory);
	        mav.addObject("globalVO", globalVO);
	        mav.addObject("boardList", adminService.getBoards(null, Board.STATUS_ACTIVE));
	        return mav;
	    }
	 /**
	  * 
	  * @param topicId
	  * @param loId
	  * @param request
	  * @param response
	  * @return
	  */
	 	@LoginAuthentication
		@RequestMapping("/admin/questionDataPageSME/{topicId}/{loId}.htm")
		public ModelAndView questionDataPageSME(@PathVariable(value="topicId") Long topicId,@PathVariable(value="loId") Long loId, HttpServletRequest request, HttpServletResponse response)
		{
			ModelAndView mav = new ModelAndView("admin/tocQuestionDataSME");
			
			
			List<QuestionTopic> qt =schoolTeacherService.getAllEnableDisableQuestionsByTopic(topicId,loId);
			mav.addObject("questionTopic",qt);
			
			//System.out.println("loid"+loId+"sdfdsvgdsgds"+qt);
			String propertyDirectoryString = Utility.getValueFromPropertyFile("flp.qa.images.retrieve");
			String questionResourceDirectory = (propertyDirectoryString.charAt(propertyDirectoryString.length()-1) != '/' ? propertyDirectoryString+"/" : propertyDirectoryString);		//"D:/FLP_RESOURCES/QA_IMAGES";
			
			GlobalVO globalVO = new GlobalVO();
			globalVO.setQuestionTypeList(schoolTeacherService.getQuestionType(QuestionType.QUESTION_TYPE, QuestionType.STATUS_ACTIVE));
			globalVO.setQuestionDifficultyLevelList(schoolTeacherService.getQuestionType(QuestionType.QUESTION_DIFFICULTY, QuestionType.STATUS_ACTIVE));
			globalVO.setQuestionCognitiveLevelList(schoolTeacherService.getQuestionType(QuestionType.QUESTION_COGNITIVE, QuestionType.STATUS_ACTIVE));
			globalVO.setZoneList(schoolTeacherService.getQuestionType(QuestionType.QUESTION_ZONE, QuestionType.STATUS_ACTIVE));
			
		
			
			globalVO.setTopicId(topicId);
			globalVO.setLoginUserType(User.USERTYPE_ADMIN);
			globalVO.setQuestionResourceDirectory(questionResourceDirectory);
			
			
			
			mav.addObject("globalVO", globalVO);
			mav.addObject("selectedlo", loId);
			
			return mav;
		}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping(value="/admin/getAdminHome.htm")
	public ModelAndView getAdminHome(HttpServletRequest request, HttpServletResponse response)
	{
		
		ModelAndView mav = new ModelAndView("superAdminheader");
		return mav;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping(value="/admin/getAdminHome_for_SME.htm")
	public ModelAndView getAdminHomeForSME(HttpServletRequest request, HttpServletResponse response)
	{
		
	
		ModelAndView mav = new ModelAndView("superAdminheaderForSME");
		return mav;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping(value = "/admin/getSubjectTocPage.htm")
	public ModelAndView getSubjectTocPage(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocSubject");

		List<Section> sectionList = adminService.getSections(null, Board.BOARD_CTLS, null, Section.STATUS_ACTIVE, null, "desc");
		StringBuilder sectionIdString = new StringBuilder("");
		for (Section section : sectionList)
		{
			sectionIdString.append(section.getId()).append(",");
		}

		mav.addObject("sectionList", sectionList);
		
		if(sectionIdString.length() > 0)
			mav.addObject("subjectSectionList", adminService.getSubjectSections(sectionIdString.substring(0, sectionIdString.length() - 1), SubjectSection.STATUS_ACTIVE));
		
		return mav;
	}
	
	/**
	 * 
	 * @param boardId
	 * @param chapterId
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping(value = "/admin/getTopicTocDataStandardLOPage/{boardId}/{chapterId}.htm")
	public ModelAndView getTopicTocDataStandardLOPage(@PathVariable(value = "boardId") Long boardId, @PathVariable(value = "chapterId") Long chapterId, HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocStandardTopicData");
		//mav.addObject("topicList", adminService.getTopics(String.valueOf(chapterId), Topic.STATUS_ACTIVE));
		mav.addObject("topicList", adminService.getAllEnableDisableTopics(chapterId, Topic.STATUS_INACTIVE));
		mav.addObject("boardListMap", adminService.getBoards(boardId, Board.STATUS_ACTIVE));
		return mav;
	}
/**
 * 
 * @param sectionIdString
 * @param subjectName
 * @param subjectId
 * @param request
 * @param response
 * @param file
 * @return
 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping(value = "/admin/addSubjectToc.htm", method = RequestMethod.POST)
	public String addSubjectToc( @RequestParam(value = "sectionIdString") String sectionIdString, 
			@RequestParam(value = "subjectName") String subjectName, @RequestParam(value = "subjectId") Long subjectId,
			HttpServletRequest request, HttpServletResponse response,@RequestParam("subjectImage") MultipartFile file)
	{
		
		
		String message = "success";
			if (sectionIdString.trim().equals(""))
			return "noSection";
		else if (subjectName.trim().equals(""))
			return "noSubject";
		else if (subjectId == null && adminService.checkSubjectAvailability( 1l,null, Subject.STATUS_ACTIVE, subjectName.trim()) == false)
			return "subjectNotAvailable";

		Subject sub = new Subject();
		sub.setId(subjectId); 
		sub.setName(subjectName.trim());
		sub.setStatus(Subject.STATUS_ACTIVE);
		//sub.setBoardId(boardId);
		sub.setSchoolId(null);

		List<Long> idList = adminService.addSubjectAndMapping(sub, sectionIdString);

		StringBuilder idString = new StringBuilder();
		
		for (Long id : idList)
		{
			idString.append(id).append(",");
		}

		message = (idString.length() > 0 ? (idString.substring(0, idString.length() - 1).toString()) : "mappingAlreadyExist");

		return message;
	}
     
	/**
	 * 
	 * @param request
	 * @param response
	 * @param mpf
	 * @return
	 */
	
	@LoginAuthentication
	@ResponseBody
	@RequestMapping(value = "/admin/newSubject.htm", method = RequestMethod.POST)
	public String newSub( HttpServletRequest request, HttpServletResponse response, @RequestParam("subjectImage")MultipartFile mpf)
	{
	   
		return "hii";
		
	}
	
	
	/**
	 * 
	 * @param subjectSectionIdString
	 * @param request
	 * @param response
	 * @return
	 */
	
	@LoginAuthentication
	@ResponseBody
	@RequestMapping(value = "/admin/removeSubjectToc.htm", method = RequestMethod.POST)
	public String removeSubjectToc(@RequestParam(value = "subjectSectionIdString") String subjectSectionIdString, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (subjectSectionIdString.trim().equals(""))
			return "noSubjectSection";

		adminService.updateSubjectSectionMapping(subjectSectionIdString, SubjectSection.STATUS_INACTIVE);

		return message;
	}
/**
 * 
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	@RequestMapping(value = "/admin/downloadExcelFormat", method = RequestMethod.GET)
	@ResponseBody
	public FileSystemResource downloadExcelFormat(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=Grade_Subject_Chapter_Topic_Format.xlsx");
		String path = Utility.resourcesFolderPath;
		return new FileSystemResource(path + "/excelFormats/Grade_Subject_Chapter_Topic_Format.xlsx");
	}
	/**
	 * 
	 * @param multiPartFile
	 * @param boardId
	 * @return
	 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping(value = "/admin/createChapterAndTopics.htm", method = RequestMethod.POST)
	public String createChapterAndTopics(@RequestParam(value="excelFile") MultipartFile multiPartFile, @RequestParam(value="boardId") Long boardId)
	{
		try
		{
			return adminService.createSubjectChapterAndTopics(multiPartFile, boardId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping(value = "/admin/getUpdatedb.htm")
	public ModelAndView getUpdateImage(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/ImagePathSetDta");
		
		return mav;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	
	@LoginAuthentication
	@RequestMapping(value = "/admin/getChapterTocPage.htm")
	public ModelAndView getChapterTocPage(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocChapter");
		mav.addObject("sectionList", adminService.getSections(null, Board.BOARD_CTLS, null, Section.STATUS_ACTIVE, null, "asc"));
		return mav;
	}
	
	@LoginAuthentication
	@RequestMapping(value = "/admin/getChapterTocPage_SME.htm")
	public ModelAndView getChapterTocPageForSME(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocChapterSME");
		mav.addObject("sectionList", adminService.getSections(null, Board.BOARD_CTLS, null, Section.STATUS_ACTIVE, null, "asc"));
		return mav;
	}
	
	
	
	
/**
 * 
 * @param gradeId
 * @param subjectId
 * @param request
 * @param response
 * @return
 */
	@LoginAuthentication
	@RequestMapping(value = "/admin/getChapterDataTocPage/{gradeId}/{subjectId}.htm", method=RequestMethod.GET)
	public ModelAndView getChapterDataTocPage(@PathVariable(value = "gradeId") Long gradeId, @PathVariable(value = "subjectId") Long subjectId, HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocChapterData");
		mav.addObject("chapterList", adminService.getChapters(String.valueOf(subjectId), gradeId, Chapter.STATUS_ACTIVE));
		mav.addObject("gradeName",request.getParameter("gradeName"));
		mav.addObject("subName", request.getParameter("subjectName"));
		return mav;
	}
/**
 * 
 * @param boardId
 * @param gradeId
 * @param sectionId
 * @param subjectId
 * @param chapterName
 * @param request
 * @param response
 * @return
 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping(value = "/admin/addChapterToc.htm", method = RequestMethod.POST)
	public String addChapterToc(@RequestParam(value = "boardId") Long boardId, @RequestParam(value = "gradeId") Long gradeId, @RequestParam(value = "sectionId") Long sectionId, @RequestParam(value = "subjectId") Long subjectId, @RequestParam(value = "chapterName") String chapterName, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (boardId == null)
			return "noBoard";
		else if (gradeId == null)
			return "noGrade";
		else if (sectionId == null)
			return "noSection";
		else if (subjectId == null)
			return "noSubject";
		else if (chapterName.trim().equals(""))
			return "noChapter";
		else if (adminService.checkChapterAvailability(gradeId, subjectId, Chapter.STATUS_ACTIVE, chapterName.trim()) == false)
			return "chapterNotAvailable";

		Chapter chapter = new Chapter();
		chapter.setBoardId(boardId);
		chapter.setGradeId(gradeId);
		chapter.setSchoolId(null);
		chapter.setSubjectId(subjectId);
		//chapter.setNameUTF8(Utility.convertStringToByteUTF8(chapterName.trim()));
		chapter.setName(chapterName.trim());
		chapter.setSerialOrder(adminService.getMaxOrderChapter(subjectId, gradeId, boardId, null));
		chapter.setModifiedDate(new Date());
		chapter.setStatus(Chapter.STATUS_ACTIVE);

		message = String.valueOf(adminService.addChapter(chapter).getId());
		return message;
	}
/**
 * 
 * @param chapterIdString
 * @param request
 * @param response
 * @return
 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping(value = "/admin/removeChapterToc.htm", method = RequestMethod.POST)
	public String removeChapterToc(@RequestParam(value = "chapterIdString") String chapterIdString, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (chapterIdString.trim().equals(""))
			return "noChapter";

		adminService.updateChapter(chapterIdString, Chapter.STATUS_INACTIVE);

		return message;
	}
/**
 * 
 * @param idList
 * @param serialOrderList
 * @param forWhat
 * @param request
 * @param response
 * @return
 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping(value="/admin/saveSerialOrder.htm", method = RequestMethod.POST)
	public String saveSerialOrder(@RequestParam(value="idArray[]") String[] idList, @RequestParam(value="serialOrderArray[]") String[] serialOrderList, @RequestParam(value="forWhat") String forWhat, HttpServletRequest request, HttpServletResponse response)
	{
		if(forWhat.equals("chapter"))
			adminService.updateChapterOrder(idList, serialOrderList);
		else if(forWhat.equals("topic"))
			adminService.updateTopicOrder(idList, serialOrderList);
		return "success";
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping(value = "/admin/getTopicTocPage.htm")
	public ModelAndView getTopicTocPage(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocTopic");
		mav.addObject("sectionList", adminService.getSections(null, Board.BOARD_CTLS, null, Section.STATUS_ACTIVE, null, "asc"));
		return mav;
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	
	@LoginAuthentication
	@RequestMapping(value = "/admin/getTopicTocPage_SME.htm")
	public ModelAndView getTopicTocPageSME(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocTopicSME");
		mav.addObject("sectionList", adminService.getSections(null, Board.BOARD_CTLS, null, Section.STATUS_ACTIVE, null, "asc"));
		return mav;
	}
/**
 * 
 * @param chapterId
 * @param request
 * @param response
 * @return
 */
	@LoginAuthentication
	@RequestMapping(value = "/admin/getTopicDataTocPage/{chapterId}.htm")
	public ModelAndView getTopicDataTocPage(@PathVariable(value = "chapterId") Long chapterId, HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocTopicData");
		mav.addObject("topicList", adminService.getTopics(String.valueOf(chapterId), Topic.STATUS_ACTIVE));
		return mav;
	}
/**
 * 
 * @param boardId
 * @param gradeId
 * @param subjectId
 * @param chapterId
 * @param topicName
 * @param request
 * @param response
 * @return
 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping(value = "/admin/addTopicToc.htm", method = RequestMethod.POST)
	public String addTopicToc(@RequestParam(value = "boardId") Long boardId, @RequestParam(value = "gradeId") Long gradeId, @RequestParam(value = "subjectId") Long subjectId, @RequestParam(value = "chapterId") Long chapterId, @RequestParam(value = "topicName") String topicName, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (boardId == null)
			return "noBoard";
		else if (gradeId == null)
			return "noGrade";
		else if (subjectId == null)
			return "noSubject";
		else if (chapterId == null)
			return "noChapter";
		else if (topicName.trim().equals(""))
			return "noTopic";
		else if (adminService.checkTopicAvailability(chapterId, Topic.STATUS_ACTIVE, topicName.trim()) == false)
			return "topicNotAvailable";

		Topic topic = new Topic();
		topic.setBoardId(boardId);
		topic.setGradeId(gradeId);
		topic.setChapterId(chapterId);
		//topic.setNameUTF8(Utility.convertStringToByteUTF8(topicName.trim()));
		topic.setName(topicName.trim());
		topic.setSubjectId(subjectId);
		topic.setSerialOrder(adminService.getMaxOrderTopic(chapterId));
		topic.setSchoolId(null);
		topic.setModifiedDate(new Date());
		topic.setStatus(Topic.STATUS_ACTIVE);

		message = String.valueOf(adminService.addTopic(topic).getId());

		return message;
	}
/**
 * 
 * @param topicIdString
 * @param request
 * @param response
 * @return
 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping(value = "/admin/removeTopicToc.htm", method = RequestMethod.POST)
	public String removeTopicToc(@RequestParam(value = "topicIdString") String topicIdString, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (topicIdString.trim().equals(""))
			return "noTopic";

		adminService.updateTopic(topicIdString, Topic.STATUS_INACTIVE);

		return message;
	}
	
	/**
	 * 
	 * @param questionId
	 * @param questopicId
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping(value = "/admin/removequestion.htm", method = RequestMethod.POST)
	public String removeQuestiondata(@RequestParam(value = "questionId") String questionId ,@RequestParam(value = "questopicId") String questopicId, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (questionId.trim().equals(""))
			return "noquestionTopicId";
	 	adminService.updateQuestion(questopicId,questionId, Topic.STATUS_INACTIVE); 
		return message;
	}
	
	
	
	
/**
 * 
 * @param request
 * @param response
 * @return
 */
	@LoginAuthentication
	@RequestMapping(value = "/admin/schoolDetails.htm")
	public ModelAndView schoolDetails(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/schoolDetails");
		mav.addObject("school", new School());
		List<School> allSchoolList = adminService.getAllSchoolDetails(false);
		if (allSchoolList != null)
		{
			mav.addObject("schoolList", allSchoolList);
		}
		List<Session> sessionList = Utility.getAllSessionList();
		if (sessionList == null)
		{
			sessionList = adminService.getAllSessionList();
			if (sessionList != null && sessionList.size() > 0)
			{
				Utility.initializeSessionList(sessionList);
			}
		}
		mav.addObject("sessionList", sessionList);
		mav.addObject("boardList", adminService.getBoards(Board.BOARD_CTLS, Board.STATUS_ACTIVE));
		return mav;
	}
/**
 * 
 * @param request
 * @param response
 * @param school
 * @return
 * @throws IOException
 */
	@LoginAuthentication
	@RequestMapping(value = "/admin/addOrEditSchool.htm", method = RequestMethod.POST)
	public ModelAndView addSchool(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("school") School school) throws IOException
	{
		Date expiryDate = Utility.convertStringToDate("dd-MM-yyyy", school.getExpDate());

		School schoolObj = null;
		if (school.getId() != null && school.getId() > 0)
		{
			schoolObj = adminService.getSchoolDetailsById(school.getId());
			if (schoolObj != null)
			{
				schoolObj.setSchoolName(school.getSchoolName());
				schoolObj.setAddress(school.getAddress());
				schoolObj.setCity(school.getCity());
				schoolObj.setContactEmail(school.getContactEmail());
				schoolObj.setContactNo(school.getContactNo());
				schoolObj.setCountry(school.getCountry());
				schoolObj.setState(school.getState());
				schoolObj.setCurrentSessionId(school.getSessionId());
				schoolObj.setExpiryDate(expiryDate);
				schoolObj.setModifiedDate(new Date());
				adminService.addSchool(schoolObj);
			}
		}
		else
		{
			school.setInsertedDate(new Date());
			school.setStatus(School.STATUS_ACTIVE);
			school.setCurrentSessionId(school.getSessionId());
			school.setId(null);
			schoolObj = school;
			schoolObj.setExpiryDate(expiryDate);
			schoolObj.setModifiedDate(new Date());
			adminService.addSchool(schoolObj);
			// save data in schoolsession table
			SchoolSession ss = new SchoolSession();
			ss.setCreatedOn(new Date());
			ss.setSchoolId(schoolObj.getId());
			ss.setSessionId(school.getSessionId());
			ss.setStatus(SchoolSession.STATUS_ACTIVE);
			adminService.saveSchoolSession(ss);
			List<School> schoolList = Utility.getAllSchoolList();
			if (schoolList != null && schoolList.size() > 0)
			{
				Utility.addSchoolInList(schoolObj);
			}
		}
		
		
		response.sendRedirect(request.getContextPath() + "/admin/schoolDetails.htm");

		return null;
	}
/**
 * 
 * @param boardId
 * @param schoolId
 * @param currentSessionId
 * @param request
 * @param response
 * @return
 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping("/admin/addSchoolAndBoardMapping.htm")
	public String addSchoolAndBoardMapping(@RequestParam(value="boardId") final Long boardId, @RequestParam(value="schoolId") final Long schoolId, @RequestParam(value="currentSessionId") final Long currentSessionId, HttpServletRequest request, HttpServletResponse response)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				
				System.out.println("=============boardId===="+boardId);
				System.out.println("============schoolId====="+schoolId);
				System.out.println("===========currentSessionId====="+currentSessionId);
				
				adminService.addSchoolAndBoardMapping(boardId, schoolId, currentSessionId);
			}
		}).start();
		
		return "";
	}
	/**
	 * 
	 * @param schoolId
	 * @param activeDeactive
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@LoginAuthentication
	@RequestMapping(value = "/admin/activeDeactiveSchool.htm", method = RequestMethod.POST)
	public ModelAndView deactivateSchool(@RequestParam("schoolId") Long schoolId, @RequestParam("activeDeactive") Integer activeDeactive, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		if (schoolId > 0)
		{
			adminService.deactivateSchool(schoolId, activeDeactive);

		}
		response.sendRedirect(request.getContextPath() + "/admin/schoolDetails.htm");
		return null;
	}
	
	
	
	
/**
 * 
 * @param request
 * @param response
 * @return
 */
	@LoginAuthentication
	@RequestMapping(value = "/admin/schoolAdminList.htm")
	public ModelAndView adminList(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/schoolAdminList");
		mav.addObject("user", new User());
		List<School> schoolList = Utility.getAllSchoolList();
		if (schoolList == null)
		{
			schoolList = adminService.getAllSchoolDetails(true);
			if (schoolList != null && schoolList.size() > 0)
			{
				Utility.initializeSchoolList(schoolList);
			}
		}

		mav.addObject("schoolList", schoolList);
		List<User> allAdminList = userService.getAllUserByRoleId(User.USERTYPE_SCHOOL_COORDINATOR);
		mav.addObject("adminList", allAdminList);
		return mav;
	}
/**
 * 
 * @param user
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	@LoginAuthentication
	@RequestMapping(value = "/admin/saveAdminRegistration.htm", method = RequestMethod.POST)
	public ModelAndView saveAdminRegistration(@ModelAttribute("user") User user, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Boolean emailStatus = userService.isEmailExist(user.getEmailId());
		if (!emailStatus)
		{
			//UserForm loginUser = (UserForm) request.getSession().getAttribute("user");
			user.setSchoolId(user.getSchoolId());
			user.setStatus(User.STATUS_ACTIVE);
			user.setCreatedBy(-1l);
			user.setCreatedDate(new Date());
			user.setUserType(User.USERTYPE_SCHOOL_COORDINATOR);
			user.setModifyDate(new Date());
			user.setUserName(user.getEmailId());
			userService.saveUserRegistration(user);
			response.sendRedirect(request.getContextPath() + "/admin/schoolAdminList.htm");
		}else{
			request.getSession().setAttribute("alreadyExits", true);
			response.sendRedirect(request.getContextPath() + "/admin/schoolAdminList.htm");
		}
		return null;
	}
/**
 * 
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	@LoginAuthentication
	@RequestMapping(value = "/admin/sessionList.htm")
	public ModelAndView sessionList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView("admin/sessionList");
		mav.addObject("session", new Session());
		List<Session> sessionList = Utility.getAllSessionList();
		if (sessionList == null)
		{
			sessionList = adminService.getAllSessionList();
			if (sessionList != null && sessionList.size() > 0)
			{
				Utility.initializeSessionList(sessionList);
			}
		}
		mav.addObject("sessionList", sessionList);
		return mav;
	}
/**
 * 
 * @param session
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	@LoginAuthentication
	@RequestMapping(value = "/admin/addSession.htm")
	public ModelAndView addSession(@ModelAttribute("session") Session session, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		session.setCreatedOn(new Date());
		session.setStartDate(Utility.convertStringToDate("dd-MM-yyyy", session.getSessionStartDate()));
		session.setEndDate(Utility.convertStringToDate("dd-MM-yyyy", session.getSessionEndDate()));
		session.setStatus(Session.STATUS_ACTIVE);
		adminService.addNewSession(session);
		List<Session> sessionList = Utility.getAllSessionList();
		if (sessionList != null && sessionList.size() > 0)
		{
			Utility.addSessionInList(session);
		}
		response.sendRedirect(request.getContextPath() + "/admin/sessionList.htm");

		return null;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping(value = "/admin/createStudentAndTeacher.htm")
	public ModelAndView createUser(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("createStudentAndTeacher");
		mav.addObject("user", new User());
		UserForm userObj = (UserForm) request.getSession().getAttribute("user");
		List<User> listUser = userService.getAllUserBySchoolId(userObj.getSchoolId());

		mav.addObject("listUser", listUser);
		return mav;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping("/admin/getBoardTocStandardPage.htm")
	public ModelAndView showCreateBoardGradePage(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocStandardBoard");
		mav.addObject("boardList", adminService.getBoards(Board.BOARD_CTLS, Board.STATUS_ACTIVE));
		return mav;
	}
/**
 * 
 * @param boardName
 * @param request
 * @param response
 * @return
 */
	 @LoginAuthentication
	@ResponseBody
	@RequestMapping("/admin/addBoardTocStandard.htm")
	public String checkNCreateNewBoard(@RequestParam(value="boardName") String boardName, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (boardName.trim().equals(""))
			return "noBoard";
		else if (adminService.checkBoardAvailability(boardName.trim(), Subject.STATUS_ACTIVE) == false)
			return "boardNotAvailable";

		Board board = new Board();
		board.setName(boardName.trim());
		board.setStatus(Board.STATUS_ACTIVE);  
		board.setModifiedDate(new Date());
		
		///System.out.println("Utility.convertDateToStringWithZone(new Date())   ..."+ Utility.convertDateToStringWithZone(new Date()) );

		board = adminService.addBoard(board);

		message = String.valueOf(board.getId());

		return message;
	}
	 
	/**
	 * 
	 * @param boardIdString
	 * @param request
	 * @param response
	 * @return
	 */
	
	
	@LoginAuthentication
	@ResponseBody
	@RequestMapping(value = "/admin/removeBoardTocStandard.htm", method = RequestMethod.POST)
	public String removeBoardToc(@RequestParam(value = "boardIdString") String boardIdString, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (boardIdString.trim().equals(""))
			return "noBoard";

		adminService.updateBoard(boardIdString, Board.STATUS_INACTIVE);

		return message;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping("/admin/getGradeTocStandardPage.htm")
	public ModelAndView getGradeTocStandardPage(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocStandardGrade");
		mav.addObject("boardList", adminService.getBoards(Board.BOARD_CTLS, Board.STATUS_ACTIVE));
		return mav;
	}
	/**
	 * 
	 * @param boardId
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping("/admin/getGradeDataTocStandardPage/{boardId}.htm")
	public ModelAndView getGradeDataTocStandardPage(@PathVariable(value="boardId") Long boardId, HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocStandardGradeData");
		mav.addObject("sectionList", adminService.getSections(null, boardId, null, Section.STATUS_ACTIVE, null, "desc"));
		mav.addObject("boardListMap", adminService.getBoards(boardId, Board.STATUS_ACTIVE));
		mav.addObject("boardIdAdmin", adminService.getBoardName(boardId).get(0).getName());
		
		return mav;
	}
	/**
	 * 
	 * @param gradeName
	 * @param boardId
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping("/admin/addGradeTocStandard.htm")
	public String addGradeTocStandard(@RequestParam(value="gradeName") String gradeName, @RequestParam(value="boardId") Long boardId, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (boardId == null)
			return "noBoard";
		else if (gradeName.trim().equals(""))
			return "noGrade";
		else if (adminService.checkGradeAvailability(gradeName.trim(), null, boardId, Grade.STATUS_ACTIVE) == false)
			return "gradeNotAvailable";

		Grade grade = new Grade();
		grade.setLevelId(1L);
		grade.setName(gradeName.trim());
		grade.setStatus(Grade.STATUS_ACTIVE);
		grade.setBoardId(boardId);
		grade.setSchoolId(null);
		grade.setModifiedDate(new Date());

		List<Long> idList = adminService.addGradeAndSection(grade, String.valueOf(boardId), null, "A");

		StringBuilder idString = new StringBuilder();
		for (Long id : idList)
		{
			idString.append(id).append(",");
		}
		
		message = idString.substring(0, idString.length() - 1).toString();

		return message;
	}
	/**
	 * 
	 * @param sectionIdString
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping(value = "/admin/removeGradeTocStandard.htm", method = RequestMethod.POST)
	public String removeGradeTocStandard(@RequestParam(value = "sectionIdString") String sectionIdString, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (sectionIdString.trim().equals(""))
			return "noSection";

		adminService.updateSection(sectionIdString, Section.STATUS_INACTIVE);

		return message;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping("/admin/getSubjectTocStandardPage.htm")
	public ModelAndView getSubjectTocStandardPage(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocStandardSubject");
		mav.addObject("boardList", adminService.getBoards(Board.BOARD_CTLS, Board.STATUS_ACTIVE));
		return mav;
	}
	/**
	 * 
	 * @param boardId
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping("/admin/getSubjectDataTocStandardPage/{boardId}.htm")
	public ModelAndView getSubjectDataTocStandardPage(@PathVariable(value="boardId") Long boardId, HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocStandardSubjectData");
		List<Section> sectionList = adminService.getSections(null, boardId, null, Section.STATUS_ACTIVE, null, "asc");
		StringBuilder sectionIdString = new StringBuilder("");
		for (Section section : sectionList)
		{
			sectionIdString.append(section.getId()).append(",");
		}

		mav.addObject("sectionList", sectionList);
		if(sectionIdString.length() > 0)
			mav.addObject("subjectSectionList", adminService.getSubjectSections(sectionIdString.substring(0, sectionIdString.length() - 1), SubjectSection.STATUS_ACTIVE));
		mav.addObject("boardListMap", adminService.getBoards(boardId, Board.STATUS_ACTIVE));
		return mav;
	}
	/**
	 * 
	 * @param boardId
	 * @param sectionIdString
	 * @param subjectName
	 * @param subjectId
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping(value = "/admin/addSubjectTocStandard.htm", method = RequestMethod.POST)
	public String addSubjectTocStandard(@RequestParam(value = "boardId") Long boardId, @RequestParam(value = "sectionIdString") String sectionIdString, @RequestParam(value = "subjectName") String subjectName, @RequestParam(value="subjectId") Long subjectId, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (boardId == null)
			return "noBoard";
		else if (sectionIdString.trim().equals(""))
			return "noSection";
		else if (subjectName.trim().equals(""))
			return "noSubject";
		else if (subjectId == null && adminService.checkSubjectAvailability(boardId, null, Subject.STATUS_ACTIVE, subjectName.trim()) == false)
			return "subjectNotAvailable";

		Subject sub = new Subject();
		sub.setId(subjectId);
		sub.setName(subjectName.trim());
		sub.setStatus(Subject.STATUS_ACTIVE);
		sub.setBoardId(boardId);
		sub.setSchoolId(null);

		List<Long> idList = adminService.addSubjectAndMapping(sub, sectionIdString);

		StringBuilder idString = new StringBuilder();
		for (Long id : idList)
		{
			idString.append(id).append(",");
		}
		
		message = (idString.length() > 0 ? (idString.substring(0, idString.length() - 1).toString()) : "mappingAlreadyExist");

		return message;
	}
	/**
	 * 
	 * @param subjectSectionIdString
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping(value = "/admin/removeSubjectTocStandard.htm", method = RequestMethod.POST)
	public String removeSubjectTocStandard(@RequestParam(value = "subjectSectionIdString") String subjectSectionIdString, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (subjectSectionIdString.trim().equals(""))
			return "noSubjectSection";

		adminService.updateSubjectSectionMapping(subjectSectionIdString, SubjectSection.STATUS_INACTIVE);

		return message;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping(value = "/admin/getChapterTocStandardPage.htm")
	public ModelAndView getChapterTocStandardPage(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocStandardChapter");
		mav.addObject("boardList", adminService.getBoards(Board.BOARD_CTLS, Board.STATUS_ACTIVE));
	
		return mav;
	}
/**
 * 
 * @param boardId
 * @param gradeId
 * @param subjectId
 * @param request
 * @param response
 * @param boardName
 * @param gradeName
 * @param subjectName
 * @return
 */
	@LoginAuthentication
	@RequestMapping(value = "/admin/getChapterTocDataStandardPage/{boardId}/{gradeId}/{subjectId}.htm")
	public ModelAndView getChapterTocDataStandardPage(@PathVariable(value = "boardId") Long boardId, @PathVariable(value = "gradeId") Long gradeId, @PathVariable(value = "subjectId") Long subjectId, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="boardName")String boardName,@RequestParam(value="gradeName")String gradeName,@RequestParam(value="subjectName")String subjectName)
	{
		ModelAndView mav = new ModelAndView("admin/tocStandardChapterData");
		mav.addObject("chapterList", adminService.getAllEnableDisableChapters(String.valueOf(subjectId), gradeId));
		mav.addObject("boardListMap", adminService.getBoards(boardId, Board.STATUS_ACTIVE));
		mav.addObject("boardName",boardName);
		mav.addObject("gradeName",gradeName);
		mav.addObject("subjectName",subjectName);
		return mav;
	}
/**
 * 
 * @param boardId
 * @param gradeId
 * @param subjectId
 * @param chapterName
 * @param request
 * @param response
 * @return
 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping(value = "/admin/addChapterTocStandard.htm", method = RequestMethod.POST)
	public String addChapterTocStandard(@RequestParam(value = "boardId") Long boardId, @RequestParam(value = "gradeId") Long gradeId, @RequestParam(value = "subjectId") Long subjectId, @RequestParam(value = "chapterName") String chapterName, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (boardId == null)
			return "noBoard";
		else if (gradeId == null)
			return "noGrade";
		else if (subjectId == null)
			return "noSubject";
		else if (chapterName.trim().equals(""))
			return "noChapter";
		else if (adminService.checkChapterAvailability(gradeId, subjectId, Chapter.STATUS_ACTIVE, chapterName.trim()) == false)
			return "chapterNotAvailable";

		Chapter chapter = new Chapter();
		chapter.setBoardId(boardId);
		chapter.setGradeId(gradeId);
		chapter.setSubjectId(subjectId); 
		chapter.setSchoolId(null);
		//chapter.setNameUTF8(Utility.convertStringToByteUTF8(chapterName.trim()));
		chapter.setName(chapterName.trim());
		chapter.setModifiedDate(new Date());
		chapter.setSerialOrder(adminService.getMaxOrderChapter(subjectId, gradeId, boardId, null));
		chapter.setStatus(Chapter.STATUS_ACTIVE);

		message = String.valueOf(adminService.addChapter(chapter).getId());
		return message;
	}
/**
 * 
 * @param chapterIdString
 * @param request
 * @param response
 * @return
 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping(value = "/admin/removeChapterTocStandard.htm", method = RequestMethod.POST)
	public String removeChapterTocStandard(@RequestParam(value = "chapterIdString") String chapterIdString, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (chapterIdString.trim().equals(""))
			return "noChapter";

		adminService.updateChapter(chapterIdString, Chapter.STATUS_INACTIVE);

		return message;
	}
	
 /**
  * 
  * @param request
  * @param response
  * @return
  */
	@LoginAuthentication
	@RequestMapping(value = "/admin/getTopicTocStandardLOPage.htm")
	public ModelAndView getTopicTocStandardLOPage(HttpServletRequest request, HttpServletResponse response)
	{
		
		ModelAndView mav = new ModelAndView("admin/tocStandardLo");
		mav.addObject("boardList", adminService.getBoards(Board.BOARD_CTLS, Board.STATUS_ACTIVE));
		
		//mav.addObject("sectionList", adminService.getSections(null, Board.BOARD_CTLS, null, Section.STATUS_ACTIVE, null, "asc"));
		return mav;
	}
	
	
	
	
	
	
	
	
	
	
	@LoginAuthentication
	@RequestMapping(value = "/admin/getTopicTocStandardLOPage_SME.htm")
	public ModelAndView getTopicTocStandardLOPageSME(HttpServletRequest request, HttpServletResponse response)
	{
		
		ModelAndView mav = new ModelAndView("admin/tocStandardLoSME");
		mav.addObject("boardList", adminService.getBoards(Board.BOARD_CTLS, Board.STATUS_ACTIVE));
		
		//mav.addObject("sectionList", adminService.getSections(null, Board.BOARD_CTLS, null, Section.STATUS_ACTIVE, null, "asc"));
		return mav;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping(value = "/admin/getTopicTocStandardPage.htm")
	public ModelAndView getTopicTocStandardPage(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocStandardTopic");
		mav.addObject("boardList", adminService.getBoards(Board.BOARD_CTLS, Board.STATUS_ACTIVE));
		return mav;
	}
/**
 * 
 * @param request
 * @param response
 * @param model
 * @return
 */
	@LoginAuthentication
	@RequestMapping(value="/admin/planNPricing")
	public ModelAndView getPlanNPricingPage(HttpServletRequest request, HttpServletResponse response, Model model){
		ModelAndView mav=new ModelAndView("/admin/planNPricing");
		List<Board> boardList= adminService.getBoards(null, Board.STATUS_ACTIVE);
		mav.addObject("boardList",boardList);
		return mav;
	}
	/**
	 * 
	 * @param boardId
	 * @param chapterId
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping(value = "/admin/getTopicTocDataStandardPage/{boardId}/{chapterId}.htm")
	public ModelAndView getTopicTocDataStandardPage(@PathVariable(value = "boardId") Long boardId, @PathVariable(value = "chapterId") Long chapterId, HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocStandardTopicData");
		//mav.addObject("topicList", adminService.getTopics(String.valueOf(chapterId), Topic.STATUS_ACTIVE));
		mav.addObject("topicList", adminService.getAllEnableDisableTopics(chapterId, Topic.STATUS_INACTIVE));
		mav.addObject("boardListMap", adminService.getBoards(boardId, Board.STATUS_ACTIVE));
		return mav;
	}
	
	/**
	 * 
	 * @param boardId
	 * @param gradeId
	 * @param subjectId
	 * @param chapterId
	 * @param topicId
	 * @param loName
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping(value = "/admin/addTopicLoPage.htm", method = RequestMethod.POST)
	public String addTopicLoPage
	(@RequestParam(value = "boardId") Long boardId, @RequestParam(value = "gradeId") 
	Long gradeId, @RequestParam(value = "subjectId") Long subjectId, @RequestParam(value = "chapterId") 
	Long chapterId, @RequestParam(value = "topicId") Long topicId,@RequestParam(value = "loName") 
	String loName, HttpServletRequest request, HttpServletResponse response)
	{ 
		
		
		String message = "success";

		if (boardId == null)
			return "noBoard";
		else if (gradeId == null)
			return "noGrade";
		else if (subjectId == null)
			return "noSubject";
		else if (chapterId == null)
			return "noChapter"; 
		else if (topicId == null)
			return "notopic";
		
		else if (loName.trim().equals(""))
			return "noloName"; 
		else if(3<=adminService.getlocount( topicId, Topic.STATUS_ACTIVE)){
			return "More Than 3 Lo not Inserted";
		} 
		else{
		LearnObjective lo = new LearnObjective();
		lo.setBoardId(boardId);
		lo.setGradeId(gradeId);
		lo.setSubjectId(subjectId);
		lo.setSchoolId(null);
		lo.setChapterId(chapterId);
		lo.setTopicId(topicId); 
		lo.setName(loName.trim());
		lo.setModifiedDate(new Date()); 
		lo.setStatus(Topic.STATUS_ACTIVE);

		message = String.valueOf(adminService.addLO(lo));
		}
		return message;
	}

/**
 * 
 * @param boardId
 * @param gradeId
 * @param subjectId
 * @param chapterId
 * @param topicName
 * @param request
 * @param response
 * @return
 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping(value = "/admin/addTopicTocStandard.htm", method = RequestMethod.POST)
	public String addTopicTocStandard(@RequestParam(value = "boardId") Long boardId, @RequestParam(value = "gradeId") Long gradeId, @RequestParam(value = "subjectId") Long subjectId, @RequestParam(value = "chapterId") Long chapterId, @RequestParam(value = "topicName") String topicName, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (boardId == null)
			return "noBoard";
		else if (gradeId == null)
			return "noGrade";
		else if (subjectId == null)
			return "noSubject";
		else if (chapterId == null)
			return "noChapter";
		else if (topicName.trim().equals(""))
			return "noTopic";
		else if (adminService.checkTopicAvailability(chapterId, Topic.STATUS_ACTIVE, topicName.trim()) == false)
			return "topicNotAvailable";

		Topic topic = new Topic();
		topic.setBoardId(boardId);
		topic.setGradeId(gradeId);
		topic.setSubjectId(subjectId);
		topic.setSchoolId(null);
		topic.setChapterId(chapterId);
		//topic.setNameUTF8(Utility.convertStringToByteUTF8(topicName.trim()));
		topic.setName(topicName.trim());
		topic.setModifiedDate(new Date());
		topic.setSerialOrder(adminService.getMaxOrderTopic(chapterId));
		topic.setStatus(Topic.STATUS_ACTIVE);

		message = String.valueOf(adminService.addTopic(topic).getId());

		return message;
	}
/**
 * 
 * @param topicIdString
 * @param request
 * @param response
 * @return
 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping(value = "/admin/removeTopicTocStandard.htm", method = RequestMethod.POST)
	public String removeTopicTocStandard(@RequestParam(value = "topicIdString") String topicIdString, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (topicIdString.trim().equals(""))
			return "noTopic";

		adminService.updateTopic(topicIdString, Topic.STATUS_INACTIVE);

		return message;
	}
	/**
	 * 
	 * @param sectionIdString
	 * @param gradeNameString
	 * @param boardIdTarget
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping("/admin/mapGradeTocStandard.htm")
	public String mapGradeTocStandard(@RequestParam(value="sectionIdString") String sectionIdString, @RequestParam(value="gradeNameString") String gradeNameString, @RequestParam(value="boardIdTarget") Long boardIdTarget, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (boardIdTarget == null)
			return "noBoardIdTarget";
		else if (sectionIdString.trim().equals(""))
			return "noSectionIdString";
		else if (gradeNameString.trim().equals(""))
			return "noGradeNameString";
		
		List<Long> idList = adminService.addGradeAndSectionMapping(sectionIdString, gradeNameString, boardIdTarget);

		StringBuilder idString = new StringBuilder();
		for (Long id : idList)
		{
			idString.append(id).append(",");
		}
		
		message = idString.substring(0, idString.length() - 1).toString();

		return message;
	}
	/**
	 * 
	 * @param boardIdTarget
	 * @param sectionIdTarget
	 * @param gradeIdSource
	 * @param gradeIdTarget
	 * @param subjectIdString
	 * @param subjectNameString
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping("/admin/mapSubjectTocStandard.htm")
	public String mapSubjectTocStandard(@RequestParam(value = "boardIdTarget") Long boardIdTarget, @RequestParam(value = "sectionIdTarget") Long sectionIdTarget, @RequestParam(value = "gradeIdSource") Long gradeIdSource, @RequestParam(value = "gradeIdTarget") Long gradeIdTarget, @RequestParam(value = "subjectIdString") String subjectIdString, @RequestParam(value = "subjectNameString") String subjectNameString, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (boardIdTarget == null)
			return "noBoardIdTarget";
		else if(sectionIdTarget == null)
			return "noSectionIdTarget";
		else if(gradeIdTarget == null)
			return "noGradeIdTarget";
		else if(gradeIdSource == null)
			return "noGradeIdSource";
		else if (subjectIdString.trim().equals(""))
			return "noSubjectIdString";
		else if (subjectNameString.trim().equals(""))
			return "noSubjectNameString";
		
		List<Long> idList = adminService.addSubjectAndSubjectSectionMapping(gradeIdSource, subjectIdString, subjectNameString, boardIdTarget, sectionIdTarget, gradeIdTarget);

		StringBuilder idString = new StringBuilder();
		for (Long id : idList)
		{
			idString.append(id).append(",");
		}
		
		message = idString.substring(0, idString.length() - 1).toString();

		return message;
	}
	/**
	 * 
	 * @param boardIdTarget
	 * @param gradeIdTarget
	 * @param subjectIdTarget
	 * @param chapterIdString
	 * @param chapterNameString
	 * @param chapterSerialOrderString
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping("/admin/mapChapterTocStandard.htm")
	public String mapChapterTocStandard(@RequestParam(value = "boardIdTarget") Long boardIdTarget, @RequestParam(value = "gradeIdTarget") Long gradeIdTarget, @RequestParam(value = "subjectIdTarget") Long subjectIdTarget, @RequestParam(value = "chapterIdString") String chapterIdString, @RequestParam(value = "chapterNameString") String chapterNameString, @RequestParam(value="chapterSerialOrderString") String chapterSerialOrderString, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (boardIdTarget == null)
			return "noBoardIdTarget";
		else if(gradeIdTarget == null)
			return "noGradeIdTarget";
		else if(subjectIdTarget == null)
			return "noSubjectIdTarget";
		else if (chapterIdString.trim().equals(""))
			return "noChapterIdString";
		else if (chapterNameString.trim().equals(""))
			return "noChapterNameString";
		
		List<Long> idList = adminService.addChapterMapping(chapterIdString, chapterSerialOrderString, chapterNameString, boardIdTarget, subjectIdTarget, gradeIdTarget);

		StringBuilder idString = new StringBuilder();
		for (Long id : idList)
		{
			idString.append(id).append(",");
		}
		
		message = idString.substring(0, idString.length() - 1).toString();

		return message;
	}
	/**
	 * 
	 * @param boardIdTarget
	 * @param gradeIdTarget
	 * @param subjectIdTarget
	 * @param chapterIdTarget
	 * @param topicIdString
	 * @param topicNameString
	 * @param topicSerialOrderString
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping("/admin/mapTopicTocStandard.htm")
 	public String mapTopicTocStandard(@RequestParam(value = "boardIdTarget") Long boardIdTarget, @RequestParam(value = "gradeIdTarget") Long gradeIdTarget, @RequestParam(value = "subjectIdTarget") Long subjectIdTarget, @RequestParam(value = "chapterIdTarget") Long chapterIdTarget, @RequestParam(value = "topicIdString") String topicIdString, @RequestParam(value = "topicNameString") String topicNameString, @RequestParam(value="topicSerialOrderString") String topicSerialOrderString, HttpServletRequest request, HttpServletResponse response)
	{
		String message = null;

		if (boardIdTarget == null)
			return "noBoardIdTarget";
		else if(chapterIdTarget == null)
			return "noChapterIdTarget";
		else if(gradeIdTarget == null)
			return "noGradeIdTarget";
		else if(subjectIdTarget == null)
			return "noSubjectIdTarget";
		else if (topicIdString.trim().equals(""))
			return "noTopicIdString";
		else if (topicNameString.trim().equals(""))
			return "noTopicNameString";
		
		List<Long> idList = adminService.addTopicMapping(topicIdString, topicSerialOrderString, topicNameString, boardIdTarget, chapterIdTarget, subjectIdTarget, gradeIdTarget);

		StringBuilder idString = new StringBuilder();
		for (Long id : idList)
		{
			idString.append(id).append(",");
		}
		
		message = idString.substring(0, idString.length() - 1).toString();

		return message;
	}
	/**
	 * 
	 * @param sectionIdSource
	 * @param sectionIdTarget
	 * @param gradeIdTarget
	 * @param boardIdTarget
	 * @param boardIdSourceMapExisted
	 * @param gradeIdSourceMapExisted
	 * @param request
	 * @param response
	 * @return
	 */
	
	@LoginAuthentication
	@ResponseBody
	@RequestMapping("/admin/mapGradeTocStandardExisted.htm")
	public String mapGradeTocStandardExisted(@RequestParam(value="sectionIdSource") Long sectionIdSource, @RequestParam(value="sectionIdTarget") Long sectionIdTarget,
			@RequestParam(value="gradeIdTarget") Long gradeIdTarget, 
			@RequestParam(value="boardIdTarget") Long boardIdTarget, 
			@RequestParam(value="boardIdSourceMapExisted" ,required=false) Long boardIdSourceMapExisted,
			@RequestParam(value="gradeIdSourceMapExisted" ,required=false) Long gradeIdSourceMapExisted,
			
			
			HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";
		
		
		if (sectionIdSource == null)
			return "noSectionIdSource";
		else if(boardIdTarget == null)
			return "noBoardIdTarget";
		else if (sectionIdTarget == null)
			return "noSectionIdTarget";
		else if (gradeIdTarget == null)
			return "noGradeIdTarget";
		
		
		//adminService.syningUpdate(sectionIdSource, boardIdTarget, gradeIdTarget, sectionIdTarget);
		
		message = adminService.addGradeAndSectionMappingExisted(sectionIdSource, boardIdTarget, gradeIdTarget, sectionIdTarget);

		return message;
	}
	
	
	@LoginAuthentication
	@ResponseBody
	@RequestMapping("/admin/mapGradeTocStandardExistedSync.htm")
	public String mapGradeTocStandardExistedSync(@RequestParam(value="sectionIdSource") Long sectionIdSource, @RequestParam(value="sectionIdTarget") Long sectionIdTarget,
			@RequestParam(value="gradeIdTarget") Long gradeIdTarget, 
			@RequestParam(value="boardIdTarget") Long boardIdTarget, 
			@RequestParam(value="boardIdSourceMapExisted" ,required=false) Long boardIdSourceMapExisted,
			@RequestParam(value="gradeIdSourceMapExisted" ,required=false) Long gradeIdSourceMapExisted,
			
			
			HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";
		
		
		if (sectionIdSource == null)
			return "noSectionIdSource";
		else if(boardIdTarget == null)
			return "noBoardIdTarget";
		else if (sectionIdTarget == null)
			return "noSectionIdTarget";
		else if (gradeIdTarget == null)
			return "noGradeIdTarget";
		
		
		//adminService.syningUpdate(sectionIdSource, boardIdTarget, gradeIdTarget, sectionIdTarget);
		
		//System.out.println("========sectionIdSource===================="+sectionIdSource);
		//System.out.println("========boardIdTarget===================="+boardIdTarget);
		//System.out.println("========gradeIdTarget===================="+gradeIdTarget);
		//System.out.println("========sectionIdTarget===================="+sectionIdTarget);
		
		
		
		
		
		message = adminService.addGradeAndSectionMappingExistedSync(sectionIdSource, boardIdTarget, gradeIdTarget, sectionIdTarget);

		return message;
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * @param gradeIdSource
	 * @param subjectIdSource
	 * @param boardIdTarget
	 * @param gradeIdTarget
	 * @param subjectIdTarget
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping("/admin/mapSubjectTocStandardExisted.htm")
	public String mapSubjectTocStandardExisted(@RequestParam(value="gradeIdSource") Long gradeIdSource, @RequestParam(value="subjectIdSource") Long subjectIdSource, @RequestParam(value="boardIdTarget") Long boardIdTarget, @RequestParam(value="gradeIdTarget") Long gradeIdTarget, @RequestParam(value="subjectIdTarget") Long subjectIdTarget, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (gradeIdSource == null)
			return "noGradeIdSource";
		else if (subjectIdSource == null)
			return "noSubjectIdSource";
		else if(boardIdTarget == null)
			return "noBoardIdTarget";
		else if (gradeIdTarget == null)
			return "noGradeIdTarget";
		else if(subjectIdTarget == null)
			return "noSubjectIdTarget";
		
		message = adminService.addSubjectAndSubjectSectionMappingExisted(gradeIdSource, subjectIdSource, boardIdTarget, gradeIdTarget, subjectIdTarget);

		return message;
	}
	/**
	 * 
	 * @param chapterIdSource
	 * @param boardIdTarget
	 * @param gradeIdTarget
	 * @param subjectIdTarget
	 * @param chapterIdTarget
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping("/admin/mapChapterTocStandardExisted.htm")
	public String mapChapterTocStandardExisted(@RequestParam(value="chapterIdSource") Long chapterIdSource, @RequestParam(value="boardIdTarget") Long boardIdTarget, @RequestParam(value="gradeIdTarget") Long gradeIdTarget, @RequestParam(value="subjectIdTarget") Long subjectIdTarget, @RequestParam(value="chapterIdTarget") Long chapterIdTarget, HttpServletRequest request, HttpServletResponse response)
	{
		String message = "success";

		if (chapterIdSource == null)
			return "noChapterIdSource";
		else if(boardIdTarget == null)
			return "noBoardIdTarget";
		else if (gradeIdTarget == null)
			return "noGradeIdTarget";
		else if (chapterIdTarget == null)
			return "noChapterIdTarget";
		else if(subjectIdTarget == null)
			return "noSubjectIdTarget";
		
		message = adminService.addChapterMappingExisted(chapterIdSource, boardIdTarget, gradeIdTarget, subjectIdTarget, chapterIdTarget);

		return message;
	}
	/**
	 * 
	 * @param topicIdSource
	 * @param boardIdTarget
	 * @param gradeIdTarget
	 * @param subjectIdTarget
	 * @param chapterIdTarget
	 * @param topicIdTarget
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping("/admin/mapTopicTocStandardExisted.htm")
	public String mapTopicTocStandardExisted(@RequestParam(value="topicIdSource") Long topicIdSource, @RequestParam(value="boardIdTarget") Long boardIdTarget, @RequestParam(value="gradeIdTarget") Long gradeIdTarget, @RequestParam(value="subjectIdTarget") Long subjectIdTarget, @RequestParam(value="chapterIdTarget") Long chapterIdTarget, @RequestParam(value="topicIdTarget") Long topicIdTarget, HttpServletRequest request, HttpServletResponse response)
	{
		String message = null;

		if (topicIdSource == null)
			return "noTopicIdSource";
		else if(boardIdTarget == null)
			return "noBoardIdTarget";
		else if (gradeIdTarget == null)
			return "noGradeIdTarget";
		else if (chapterIdTarget == null)
			return "noChapterIdTarget";
		else if(subjectIdTarget == null)
			return "noSubjectIdTarget";
		else if(topicIdTarget == null)
			return "noTopicIdTarget";
		
		message = adminService.addTopicMappingExisted(topicIdSource, boardIdTarget, gradeIdTarget, subjectIdTarget, chapterIdTarget, topicIdTarget);

		return message;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping("/admin/questionPage.htm")
	public ModelAndView questionPage(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocQuestion");
		mav.addObject("sectionList", adminService.getSections(null, Board.BOARD_CTLS, null, Section.STATUS_ACTIVE, null, "asc"));
		
		return mav;
	}
	
	
	
	@LoginAuthentication
	@RequestMapping("/admin/questionPage_SME.htm")
	public ModelAndView questionPageSME(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocQuestionSME");
		mav.addObject("sectionList", adminService.getSections(null, Board.BOARD_CTLS, null, Section.STATUS_ACTIVE, null, "asc"));
		
		return mav;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/downloadQuestionExcelFormat", method = RequestMethod.GET)
	@ResponseBody
	public FileSystemResource downloadQuestionExcelFormat(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=Question_Format.xlsx");
		String path = Utility.resourcesFolderPath;
		return new FileSystemResource(path + "/excelFormats/Question_Format.xlsx");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/downloadQuestionExcelFormatGradewise", method = RequestMethod.GET)
	@ResponseBody
	public FileSystemResource downloadQuestionExcelFormatGradewise(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=Grade_Subjectwise_Question_format.xlsx");
		String path = Utility.resourcesFolderPath;
		return new FileSystemResource(path + "/excelFormats/Grade_Subjectwise_Question_format.xlsx");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/downloadExistingQuestionFormat", method = RequestMethod.GET)
	@ResponseBody
	public FileSystemResource downloadExistingQuestionFormat(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename= Grade-Subject-ExistingQuestions-Format.xlsx");
		String path = Utility.resourcesFolderPath;
		return new FileSystemResource(path + "/excelFormats/Grade-Subject-ExistingQuestions-Format.xlsx");
	}
	
	
	
	/**
	 * 
	 * @param topicId
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping("/admin/questionDataPage/{topicId}.htm")
	public ModelAndView questionDataPage(@PathVariable(value="topicId") Long topicId, HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocQuestionData");
		
		//mav.addObject("sectionList", adminService.getSections(null, Board.BOARD_CTLS, null, Section.STATUS_ACTIVE, null));
		List<QuestionTopic> qt =schoolTeacherService.getAllQuestionsByTopic(topicId);
		mav.addObject("questionTopic",qt);
		
		
		String propertyDirectoryString = Utility.getValueFromPropertyFile("flp.qa.images.retrieve");
		String questionResourceDirectory = (propertyDirectoryString.charAt(propertyDirectoryString.length()-1) != '/' ? propertyDirectoryString+"/" : propertyDirectoryString);		//"D:/FLP_RESOURCES/QA_IMAGES";
		
		GlobalVO globalVO = new GlobalVO();
		globalVO.setQuestionTypeList(schoolTeacherService.getQuestionType(QuestionType.QUESTION_TYPE, QuestionType.STATUS_ACTIVE));
		globalVO.setQuestionDifficultyLevelList(schoolTeacherService.getQuestionType(QuestionType.QUESTION_DIFFICULTY, QuestionType.STATUS_ACTIVE));
		globalVO.setQuestionCognitiveLevelList(schoolTeacherService.getQuestionType(QuestionType.QUESTION_COGNITIVE, QuestionType.STATUS_ACTIVE));
		globalVO.setTopicId(topicId);
		globalVO.setLoginUserType(User.USERTYPE_ADMIN);
		globalVO.setQuestionResourceDirectory(questionResourceDirectory);
		mav.addObject("globalVO", globalVO);
		
		return mav;
	}
	
	 @LoginAuthentication
		@ResponseBody
		@RequestMapping("/admin/uploadQuestionUpdate.htm")
		public String uploadQuestionUpdate(@ModelAttribute(value = "globalVO") GlobalVO globalVO, HttpServletRequest request, HttpServletResponse response) throws IOException
		{
			String message = "success";
			
			message = schoolTeacherService.saveUpdateQuestionUpdate(globalVO);
			
			return message;
		}
	
	
	/**
	 * 
	 * @param globalVO
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping("/admin/uploadQuestion.htm")
	public String uploadQuestion(@ModelAttribute(value = "globalVO") GlobalVO globalVO, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String message = "success";
		
		

		
		message = schoolTeacherService.saveUpdateQuestion(globalVO);
		
		return message;
	}
	/**
	 * 
	 * @param globalVO
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	
	@LoginAuthentication
	@RequestMapping("/admin/updateQuestionComment.htm")
	public String updateQuestionComment(@ModelAttribute(value = "globalVO") GlobalVO globalVO, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		
		schoolTeacherService.saveUpdateQuestionComment(globalVO);
		response.sendRedirect(request.getContextPath() + "/admin/getQuestionDetailsById/"+globalVO.getQuestionId()+".htm");
		return null;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param mpf
	 * @return
	 */
		@LoginAuthentication
		@RequestMapping(value = "/admin/uploadLoQuestionExcelSheet", method = { RequestMethod.POST, RequestMethod.GET })
		@ResponseBody
		public String uploadLo(MultipartHttpServletRequest request, HttpServletResponse response,@RequestParam("file2") MultipartFile mpf)
		{
			UserForm loginUser = (UserForm) request.getSession().getAttribute("user");

			long topicId = Long.parseLong(request.getParameter("topicId"));
			long  loid= Long.parseLong(request.getParameter("loId"));
		
			String msg = null;
			try
			{
				//Iterator<String> itr = request.getFileNames();
				//MultipartFile mpf = request.getFile(itr.next());
				File excelFile = new File(mpf.getOriginalFilename());
				String fileName = excelFile.getName();
				mpf.transferTo(excelFile);

				if (fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).equals("xlsx") || fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).equals("xls"))
					msg = schoolTeacherService.uploadLoQuestionFile(excelFile, loginUser.getId(), loginUser.getSchoolId(), topicId,loid);
				else
					msg= "Please provide excel file";
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
	    	return msg;
		}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param mpf
	 * @return
	 */
		
	@LoginAuthentication
	@RequestMapping(value = "/admin/uploadQuestionExcel.html", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String upload(MultipartHttpServletRequest request, HttpServletResponse response,@RequestParam("file2") MultipartFile mpf)
	{
		UserForm loginUser = (UserForm) request.getSession().getAttribute("user");

		long topicId = Long.parseLong(request.getParameter("topicId"));
		String msg = null;
		try
		{
			
			File excelFile = new File(mpf.getOriginalFilename());
			String fileName = excelFile.getName();
			mpf.transferTo(excelFile);

			if (fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).equals("xlsx") || fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).equals("xls"))
				msg = schoolTeacherService.uploadQuestionFile(excelFile, loginUser.getId(), loginUser.getSchoolId(), topicId);
			else
				msg= "Please provide excel file";
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
    	return msg;
	}
	/**
	 * 	
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping("/admin/modulePage.htm")
	public ModelAndView modulePage(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocModule");
		mav.addObject("sectionList", adminService.getSections(null, Board.BOARD_CTLS, null, Section.STATUS_ACTIVE, null, "asc"));
		
		return mav;
	}
	/**
	 * 
	 * @param topicId
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping("/admin/moduleDataPage/{topicId}.htm")
	public ModelAndView moduleDataPage(@PathVariable(value="topicId") Long topicId, HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocModuleData");
		
		String propertyDirectoryString = Utility.getValueFromPropertyFile("flp.modules.retrieve");
		String moduleResourceDirectory = (propertyDirectoryString.charAt(propertyDirectoryString.length()-1) != '/' ? propertyDirectoryString+"/" : propertyDirectoryString);		//"D:/FLP_RESOURCES/MODULES";
		
		GlobalVO globalVO = new GlobalVO();
		globalVO.setResourceTypeList(schoolTeacherService.getModuleTypes(ModuleType.MODULE_RESOURCE_TYPE, ModuleType.STATUS_ACTIVE));
		globalVO.setGradeTypeList(schoolTeacherService.getModuleTypes(ModuleType.MODULE_GRADE_TYPE, ModuleType.STATUS_ACTIVE));
		globalVO.setTopicId(topicId);
		globalVO.setLoginUserType(User.USERTYPE_ADMIN);
		globalVO.setModuleResourceDirectory(moduleResourceDirectory);
		mav.addObject("globalVO", globalVO);
		mav.addObject("moduleTopicList", adminService.getModuleTopics(null, topicId, ModuleTopic.STATUS_ACTIVE));
		
		return mav;
	}
	/**
	 * 
	 * @param globalVO
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping("/admin/uploadModule.htm")
	public String uploadModule(@ModelAttribute(value = "globalVO") GlobalVO globalVO, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String message = "success";
		
		//globalVO.setBoardId(Board.BOARD_CTLS);
		
		message = schoolTeacherService.uploadModule(globalVO);
		
		return message;
	}
	/**
	 * 
	 * @param globalVO
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping("/admin/uploadResources.htm")
	public String uploadResources(@ModelAttribute(value = "globalVO") GlobalVO globalVO, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		
		
		String message = "success";
		UserForm loginUser = (UserForm) request.getSession().getAttribute("user");
		globalVO.setCreatedBy(loginUser.getId());
		//globalVO.setBoardId(Board.BOARD_CTLS);
		
		message = schoolTeacherService.uploadResources(globalVO);
		
		return message;
	}
	
	/**
	 * 
	 * @param globalVO
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@LoginAuthentication
	@ResponseBody
	@RequestMapping("/admin/updateResources.htm")
	public String updateResources(@ModelAttribute(value ="globalVO") GlobalVO globalVO , HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String message="success";
	
		UserForm loginUser =(UserForm) request.getSession().getAttribute("user");
		globalVO.setCreatedBy(loginUser.getId());
		
		message=schoolTeacherService.updateResource(globalVO);
		
		
		return message;
		
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping("/admin/questionPageStandard.htm")
	public ModelAndView questionPageStandard(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocStandardQuestion");
		mav.addObject("boardList", adminService.getBoards(Board.BOARD_CTLS, Board.STATUS_ACTIVE));
		
		return mav;
	}
	/**
	 * 	
	 * @param topicId
	 * @param loid
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping("/admin/questionDataPageStandard/{topicId}/{loid}.htm")
	public ModelAndView questionDataPageStandard(@PathVariable(value="topicId") Long topicId,@PathVariable(value="loid") Long loid, HttpServletRequest request, HttpServletResponse response)
	{
		
		ModelAndView mav = new ModelAndView("admin/tocStandardQuestionData");
		
		
		mav.addObject("questionTopic", schoolTeacherService.getAllEnableDisableQuestionsByTopic(topicId,loid));
		
		String propertyDirectoryString = Utility.getValueFromPropertyFile("flp.qa.images.retrieve");
		String questionResourceDirectory = (propertyDirectoryString.charAt(propertyDirectoryString.length()-1) != '/' ? propertyDirectoryString+"/" : propertyDirectoryString);		//"D:/FLP_RESOURCES/QA_IMAGES";
		
		GlobalVO globalVO = new GlobalVO();
		globalVO.setQuestionTypeList(schoolTeacherService.getQuestionType(QuestionType.QUESTION_TYPE, QuestionType.STATUS_ACTIVE));
		globalVO.setQuestionDifficultyLevelList(schoolTeacherService.getQuestionType(QuestionType.QUESTION_DIFFICULTY, QuestionType.STATUS_ACTIVE));
		globalVO.setQuestionCognitiveLevelList(schoolTeacherService.getQuestionType(QuestionType.QUESTION_COGNITIVE, QuestionType.STATUS_ACTIVE));
		
		
		globalVO.setZoneList(schoolTeacherService.getQuestionType(QuestionType.QUESTION_ZONE, QuestionType.STATUS_ACTIVE));
		globalVO.setLoId(loid);
		globalVO.setTopicId(topicId);
		globalVO.setLoginUserType(User.USERTYPE_ADMIN);
		globalVO.setQuestionResourceDirectory(questionResourceDirectory);
		mav.addObject("globalVO", globalVO);
		mav.addObject("boardList", adminService.getBoards(null, Board.STATUS_ACTIVE));
		return mav;
	}
	/**
	 * 	
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping("/admin/modulePageStandard.htm")
	public ModelAndView modulePageStandard(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocStandardModule");
		mav.addObject("boardList", adminService.getBoards(Board.BOARD_CTLS, Board.STATUS_ACTIVE));
		
		return mav;
	}
	/**
	 * 
	 * @param topicId
	 * @param request
	 * @param response
	 * @return
	 */
	@LoginAuthentication
	@RequestMapping("/admin/moduleDataPageStandard/{topicId}.htm")
	public ModelAndView moduleDataPageStandard(@PathVariable(value="topicId") Long topicId, HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/tocStandardModuleData");
		
		String propertyDirectoryString = Utility.getValueFromPropertyFile("flp.modules.retrieve");
		String moduleResourceDirectory = (propertyDirectoryString.charAt(propertyDirectoryString.length()-1) != '/' ? propertyDirectoryString+"/" : propertyDirectoryString);		//"D:/FLP_RESOURCES/MODULES";
		
		GlobalVO globalVO = new GlobalVO();
		globalVO.setResourceTypeList(schoolTeacherService.getModuleTypes(ModuleType.MODULE_RESOURCE_TYPE, ModuleType.STATUS_ACTIVE));
		globalVO.setGradeTypeList(schoolTeacherService.getModuleTypes(ModuleType.MODULE_GRADE_TYPE, ModuleType.STATUS_ACTIVE));
		globalVO.setTopicId(topicId);
		globalVO.setLoginUserType(User.USERTYPE_ADMIN);
		globalVO.setModuleResourceDirectory(moduleResourceDirectory);
		mav.addObject("globalVO", globalVO);
		mav.addObject("moduleTopicList", adminService.getModuleTopics(null, topicId, ModuleTopic.STATUS_ACTIVE));
		
		return mav;
	}
	/**
	 * 
	 * @param questionId
	 * @param request
	 * @param response
	 * @return
	 */
	
	@LoginAuthentication
	@RequestMapping(value="/admin/getQuestionDetailsById/{questionId}.htm")
	public ModelAndView getQuestionDetailsById(@PathVariable Long questionId, HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView("admin/questionDetails");
		Question questDetails=adminService.getQuestionDetailsById(questionId);
		List<QuestionComments> commentsLst=adminService.getAllCommentsByQuestionId(questionId);
		List<QuestionType> questType=schoolTeacherService.getQuestionType(QuestionType.QUESTION_TYPE, QuestionType.STATUS_ACTIVE);
		List<QuestionType> difficultType=schoolTeacherService.getQuestionType(QuestionType.QUESTION_DIFFICULTY, QuestionType.STATUS_ACTIVE);
		List<QuestionType> cognitiveType=schoolTeacherService.getQuestionType(QuestionType.QUESTION_COGNITIVE, QuestionType.STATUS_ACTIVE);
		String propertyDirectoryString = Utility.getValueFromPropertyFile("flp.qa.images.retrieve");
		String questionResourceDirectory = (propertyDirectoryString.charAt(propertyDirectoryString.length()-1) != '/' ? propertyDirectoryString+"/" : propertyDirectoryString);		//"D:/FLP_RESOURCES/QA_IMAGES";
		mav.addObject("questionResourceDirectory", questionResourceDirectory);
		mav.addObject("questionDetails", questDetails);
		mav.addObject("commentsLst", commentsLst);
		mav.addObject("questType", questType);
		mav.addObject("difficultType", difficultType);
		mav.addObject("cognitiveType", cognitiveType);
		mav.addObject("globalVO", new GlobalVO());
		return mav;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/admin/savePrice" ,method=RequestMethod.POST)
	@LoginAuthentication
	public String savePrice(HttpServletRequest request, HttpServletResponse response)
	{
		return  adminService.savePrice(request);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param excelSheet
	 * @return
	 */
		@ResponseBody
		@RequestMapping(value="admin/uploadQuestionGradeSubjectWise.htm" ,method ={RequestMethod.POST, RequestMethod.GET})
		@LoginAuthentication
	    public String fileupload(HttpServletRequest request,HttpServletResponse response,@RequestParam("gradeWiseExcel") MultipartFile excelSheet){
			UserForm loginUser = (UserForm) request.getSession().getAttribute("user");

			String msg = null;
			try
			{
				//Iterator<String> itr = request.getFileNames();
				//MultipartFile mpf = request.getFile(itr.next());
				File excelFile = new File(excelSheet.getOriginalFilename());
				String fileName = excelFile.getName();
				excelSheet.transferTo(excelFile);

				if (fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).equals("xlsx") || fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).equals("xls"))
					msg = schoolTeacherService.uploadQuestionWithGradeAndSubjectwise(excelFile, loginUser.getId(), loginUser.getSchoolId());
				else
					msg= "Please provide excel file";
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
	    	return msg;
		
		}
		/**
		 * 
		 * @param request
		 * @param response
		 * @return
		 */
		
		@LoginAuthentication
		@RequestMapping("/admin/questionUploadaPageStandard.htm")
		public ModelAndView questionDataPageStandard(HttpServletRequest request, HttpServletResponse response)
		{
		ModelAndView mav = new ModelAndView("admin/tocQuestionGradeSubjectwise");
			return mav;
		}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param subjectImageNew
	 * @return
	 * @throws IOException
	 */
		
		
		@LoginAuthentication
		@RequestMapping(value="/admin/updateSubject.htm", method=RequestMethod.POST)
		public String updateSubject(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="subjectImageNew",required=false) MultipartFile subjectImageNew) throws IOException
		{
			UserForm loginUser = (UserForm) request.getSession().getAttribute("user");
			String swName=null;
			if(subjectImageNew.getSize()>0l){
				swName=saveSubjectImage(subjectImageNew);
			}
			adminService.updateSubject(request, loginUser.getId(),swName);
			String pageType=request.getParameter("type");
			if(pageType.equals("ctls")){
			response.sendRedirect(request.getContextPath()+"/admin/getSubjectTocPage.htm");
			}else if(pageType.equals("nonCtls")){
				Long boardId=Long.parseLong(request.getParameter("boardId"));
				request.getSession().setAttribute("boardIds5", boardId);
				response.sendRedirect(request.getContextPath()+"/admin/getSubjectTocStandardPage.htm");
			}
			return null;
		}
		/**
		 * 
		 * @param request
		 * @param response
		 * @return
		 */
		
		@ResponseBody
		@LoginAuthentication
		@RequestMapping(value="/admin/updateTopic.htm", method=RequestMethod.POST)
		public String updateTopic(HttpServletRequest request, HttpServletResponse response)      
		{
			UserForm loginUser = (UserForm) request.getSession().getAttribute("user");
			return adminService.updateTopic(request, loginUser.getId());
		}
		/**
		 * 
		 * @param request
		 * @param response
		 * @return
		 */
		@ResponseBody
		@LoginAuthentication
		@RequestMapping(value="/admin/updateChapter.htm", method=RequestMethod.POST)
		public String updateChapter(HttpServletRequest request, HttpServletResponse response)
		{
			UserForm loginUser = (UserForm) request.getSession().getAttribute("user");
			return adminService.updateChapter(request, loginUser.getId());
		}
		/**
		 * 
		 * @param request
		 * @param response
		 * @return
		 */
		@ResponseBody
		@LoginAuthentication
		@RequestMapping(value="/admin/updateGrade.htm", method=RequestMethod.POST)
		public String updateGrade(HttpServletRequest request, HttpServletResponse response)
		{
			UserForm loginUser = (UserForm) request.getSession().getAttribute("user");
			return adminService.updateGrade(request, loginUser.getId());
		}
		/**
		 * 
		 * @param request
		 * @param response
		 * @return
		 */
		@ResponseBody
		@LoginAuthentication
		@RequestMapping(value="/admin/updateBoard.htm", method=RequestMethod.POST)
		public String updateBoard(HttpServletRequest request, HttpServletResponse response)
		{
			UserForm loginUser = (UserForm) request.getSession().getAttribute("user");
			return adminService.updateBoard(request, loginUser.getId());
		}
		/**
		 * 
		 * @param request
		 * @param response
		 * @param topicId
		 * @return
		 * @throws FileNotFoundException
		 * @throws IOException
		 */
		@ResponseBody
		@LoginAuthentication
		@RequestMapping(value="/admin/downloadQuestion.htm",method=RequestMethod.GET)
		public FileSystemResource downloadQuestions(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="topicId")Long topicId) throws FileNotFoundException, IOException{
			List<QuestionTopic> questionTopic = schoolTeacherService.getAllQuestionsByTopic(topicId);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=Question_Format.xlsx");
		//	String path = Utility.resourcesFolderPath;
				File  file =new File("Question_Format.xlsx");
			
				
			XSSFWorkbook workbook=new XSSFWorkbook();
			
			XSSFSheet sheet=workbook.createSheet("Questions");
			int rowCount=0;
			Row initRow=sheet.createRow(0);
			initRow.createCell(0).setCellValue("Question");;
			initRow.createCell(1).setCellValue("Option 1");
			initRow.createCell(2).setCellValue("Option 2");
			initRow.createCell(3).setCellValue("Option 3");
			initRow.createCell(4).setCellValue("Option 4");
			initRow.createCell(5).setCellValue("Answer");
			initRow.createCell(6).setCellValue("Solution");
			initRow.createCell(7).setCellValue("Hint 1");
			initRow.createCell(8).setCellValue("Hint 2");
			initRow.createCell(9).setCellValue("Hint 3");
			initRow.createCell(10).setCellValue("Difficulty Level");
			initRow.createCell(11).setCellValue("Cognitive Level");
			initRow.createCell(12).setCellValue("Image Present");
			initRow.createCell(13).setCellValue("Question Image");
			initRow.createCell(14).setCellValue("Option 1 Image");
			initRow.createCell(15).setCellValue("Option 2 Image");
			initRow.createCell(16).setCellValue("Option 3 Image");
			initRow.createCell(17).setCellValue("Option 4 Image");
			
			for(QuestionTopic qt:questionTopic){
				Row row=sheet.createRow(++rowCount);
				Question q=qt.getQuestion();
				row.createCell(0).setCellValue(q.getDescription());
				row.createCell(6).setCellValue(q.getAnsSolution());
				row.createCell(7).setCellValue(q.getHint());
				row.createCell(8).setCellValue(q.getHint2());
				row.createCell(9).setCellValue(q.getHint3());
				row.createCell(10).setCellValue(qt.getDifficultyLevel().getName());
				row.createCell(11).setCellValue(qt.getCognitiveLevel().getName());
				row.createCell(13).setCellValue(q.getImagePath());
				Integer i=1;
				for(Answer a:q.getAnswers()){
					row.createCell(i).setCellValue(a.getDescription());
					row.createCell(i+13).setCellValue(a.getImagePath());
					
					if(a.getIsCorrect() == 1){
						row.createCell(5).setCellValue(""+i);
					}
					i++;
				}

				Cell celImage = row.createCell(12);
				if(q.getImagePresent()== null){
					celImage.setCellValue(" ");
				}
				else if(q.getImagePresent()==1){
					celImage.setCellValue("true");
				}else{
					celImage.setCellValue("false");
				}
			
			}
			
			
				FileOutputStream outputStream = new FileOutputStream(file) ;
	            workbook.write(outputStream);
	            outputStream.close();
			
			return new FileSystemResource(file);
		}
		/**
		 * 
		 * @param request
		 * @param response
		 * @param gradeId
		 * @param boardName
		 * @param gradeName
		 * @param subjectName
		 * @param subjectId
		 * @return
		 * @throws FileNotFoundException
		 * @throws IOException
		 */
		@ResponseBody
		@LoginAuthentication
		@RequestMapping(value="/admin/getSubjectQuestions/{boardId}/{gradeId}/{subjectId}.htm",method=RequestMethod.GET)
		public FileSystemResource downloadSubjectQuestions(HttpServletRequest request, HttpServletResponse response,@PathVariable(value = "gradeId") String gradeId,@RequestParam(value = "boardName") String boardName,@RequestParam(value = "gradeName") String gradeName,@RequestParam(value = "subjectName") String subjectName, @PathVariable(value = "subjectId") String subjectId) throws FileNotFoundException, IOException{
			
			String filePath= gradeName.trim().replace(" ", "-")+"_"+subjectName.trim().replace(" ", "-")+"_Questions.xlsx";
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename="+filePath);
			
					File file = new File(filePath);
			
			
			List<QuestionTopic> questionTopic = adminService.getSubjectWiseQuestions(Long.valueOf(subjectId), Long.valueOf(gradeId));
		
			
			
			XSSFWorkbook workbook=new XSSFWorkbook();
			XSSFSheet sheet=workbook.createSheet(subjectName);
			int rowNum=0;
			Row row =sheet.createRow(rowNum);
			
			row.createCell(0).setCellValue("Board");
			row.createCell(1).setCellValue("Grade");
			row.createCell(2).setCellValue("Subject");
			row.createCell(3).setCellValue("Chapter");
			row.createCell(4).setCellValue("Topic");
			row.createCell(5).setCellValue("Question Id");
			row.createCell(6).setCellValue("Question");
			row.createCell(7).setCellValue("Option 1");
			row.createCell(8).setCellValue("Option 2");
			row.createCell(9).setCellValue("Option 3");
			row.createCell(10).setCellValue("Option 4");
			row.createCell(11).setCellValue("Answer");
			row.createCell(12).setCellValue("Solution");
			row.createCell(13).setCellValue("Hint 1");
			row.createCell(14).setCellValue("Hint 2");
			row.createCell(15).setCellValue("Hint 3");
			row.createCell(16).setCellValue("Difficulty Level");
			row.createCell(17).setCellValue("Cognitive Level");
			row.createCell(18).setCellValue("Image Present");
			row.createCell(19).setCellValue("Question Image");
			row.createCell(20).setCellValue("Option 1 Image");
			row.createCell(21).setCellValue("Option 2 Image");
			row.createCell(22).setCellValue("Option 3 Image");
			row.createCell(23).setCellValue("Option 4 Image");
			
			
					
					
					for(QuestionTopic qt: questionTopic){
						
						Row newRow=sheet.createRow(++rowNum);
						newRow.createCell(0).setCellValue(boardName);
						newRow.createCell(1).setCellValue(gradeName);
						newRow.createCell(2).setCellValue(subjectName);
						newRow.createCell(3).setCellValue(qt.getTopic().getChapter().getName());
						newRow.createCell(4).setCellValue(qt.getTopic().getName());
						
						Question q=qt.getQuestion();
						newRow.createCell(5).setCellValue(q.getId());
						newRow.createCell(6).setCellValue(q.getDescription());
						
						Integer i=7;
						for(Answer a:q.getAnswers()){
							newRow.createCell(i).setCellValue(a.getDescription());
							newRow.createCell(i+13).setCellValue(a.getImagePath());
							if(a.getIsCorrect() == 1){
								newRow.createCell(11).setCellValue(""+(i-6));
							}
							
							
							i++;
						}
						
						newRow.createCell(12).setCellValue(q.getAnsSolution());
						newRow.createCell(13).setCellValue(q.getHint());
						newRow.createCell(14).setCellValue(q.getHint2());
						newRow.createCell(15).setCellValue(q.getHint3());
						
						newRow.createCell(16).setCellValue(qt.getDifficultyLevel().getName());
						newRow.createCell(17).setCellValue(qt.getCognitiveLevel().getName());
						if(q.getImagePresent()== null){
							newRow.createCell(18).setCellValue(" ");
						}
						else if(q.getImagePresent()==1){
							newRow.createCell(18).setCellValue("true");
						}else{
							newRow.createCell(18).setCellValue("false");
						}
						newRow.createCell(19).setCellValue(q.getImagePath());
						
						
					}
					
			
			
			FileOutputStream outputStream = new FileOutputStream(file) ;
            workbook.write(outputStream);
            outputStream.close();
			return new FileSystemResource(file);
		}
		/**
		 * 
		 * @param request
		 * @param response
		 * @return
		 */
		
		@LoginAuthentication
		@RequestMapping(value="/admin/updatePage.htm",method = RequestMethod.GET )
		public ModelAndView updateQuestionsviaExcel(HttpServletRequest request,HttpServletResponse response){
			
			ModelAndView mav=new ModelAndView("admin/updateExistingQuestionsAdmin");
			
			
			return mav;
		}
		
		/**
		 * 
		 * @param request
		 * @param response
		 * @param excelSheet
		 * @return
		 */
		
			@ResponseBody
			@RequestMapping(value="admin/updateExistingQuestions.htm" ,method ={RequestMethod.POST, RequestMethod.GET})
			@LoginAuthentication
		    public String updateExistingQuestions(HttpServletRequest request,HttpServletResponse response,@RequestParam("questionExcel") MultipartFile excelSheet){
				UserForm loginUser = (UserForm) request.getSession().getAttribute("user");

				String msg = null;
				try
				{
					File excelFile = new File(excelSheet.getOriginalFilename());
					String fileName = excelFile.getName();
					excelSheet.transferTo(excelFile);

					if (fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).equals("xlsx") || fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).equals("xls"))
						msg = schoolTeacherService.updateExistingQuestions(excelFile, loginUser.getId(), loginUser.getSchoolId());
					else
						msg= "Please provide excel file";
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
		    	return msg;
			
			}
			
			/**
			 * 
			 * @param boardId
			 * @param sectionIdString
			 * @param subjectName
			 * @param subjectId
			 * @param request
			 * @param response
			 * @param subjectImage
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value="admin/updateSubjectAndImage.htm" ,method ={RequestMethod.POST, RequestMethod.GET})
			@LoginAuthentication
		    public String updateSubjectAndImage(@RequestParam Long boardId,@RequestParam String  sectionIdString,
		    		@RequestParam String subjectName ,@RequestParam Long subjectId 
		    		 		,HttpServletRequest request,HttpServletResponse response,
		    		 		@RequestParam(value="subjectImage",required=false) MultipartFile subjectImage){
				UserForm loginUser = (UserForm) request.getSession().getAttribute("user");
				
				
				

				String msg = null;

				Subject sub = new Subject();
				
				if(subjectImage.getSize()==0)
				{
					
					if (sectionIdString.trim().equals(""))
						return "noSection";
					else if (subjectName.trim().equals(""))
						return "noSubject";
					
					else{
					//	Subject sub = new Subject();
						sub.setId(subjectId); 
						sub.setName(subjectName.trim());
						
						sub.setStatus(Subject.STATUS_ACTIVE);
						sub.setBoardId(boardId);
						sub.setSchoolId(null);

						List<Long> idList = adminService.addSubjectAndMapping(sub, sectionIdString);

						StringBuilder idString = new StringBuilder();
						
						for (Long id : idList)
						{
							idString.append(id).append(",");
						}

						msg = (idString.length() > 0 ? (idString.substring(0, idString.length() - 1).toString()) : "mappingAlreadyExist");

					}
						
				return msg;
				
				}
				else
				{
					if (sectionIdString.trim().equals(""))
						return "noSection";
					else if (subjectName.trim().equals(""))
						return "noSubject";
					else if (subjectId.equals("") && adminService.checkSubjectAvailability( boardId,null, Subject.STATUS_ACTIVE, subjectName.trim()) == false)
						return "subjectAvailable";
					else
					{
				String swName=saveSubjectImage(subjectImage);
						sub.setName(subjectName.trim());
						
						sub.setStatus(Subject.STATUS_ACTIVE);
						sub.setBoardId(boardId);
						sub.setSubImage(swName);
						sub.setSchoolId(null);

						List<Long> idList = adminService.addSubjectAndMapping(sub, sectionIdString);
						
						
						
						
						
					
				
						StringBuilder idString = new StringBuilder();
						
						for (Long id : idList)
						{
							idString.append(id).append(",");
						}

						msg = (idString.length() > 0 ? (idString.substring(0, idString.length() - 1).toString()) : "mappingAlreadyExist");

						
					}
	
				}
				
				

				
						    	return msg;
			
			}

			/**
			 * 
			 * @param subjectImage
			 * @return
			 */
			private String saveSubjectImage(MultipartFile subjectImage)
			{
				String fileName="";
				File excelFile = new File(subjectImage.getOriginalFilename());
				fileName = excelFile.getName();
				String ext=fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
				Calendar d=Calendar.getInstance();
				Long name=d.getTimeInMillis();
				String swName=name.toString().concat("."+ext);
				Utility.saveImageInResources(subjectImage,new File(env.getRequiredProperty("flp.subjectImage")),swName);
				return swName;
			}
			/**
			 * 
			 * @param request
			 * @param respose
			 * @param boardId
			 * @param gradeId
			 * @return
			 */
			@RequestMapping(value="/admin/syncContent")
			public ModelAndView topicSyncing(HttpServletRequest request,HttpServletResponse respose,@RequestParam Long boardId,@RequestParam Long gradeId)
			{
				
				List<Grade> ls=adminService.syncingStatus(boardId,gradeId);
					for(Grade obj:ls)
					{
						
						
						adminService.addGradeAndSectionMappingExistedMerge(obj.getSectionIdSource(),obj.getBoardIdTarget(), obj.getGradeIdTarget(),obj.getSectionIdTarget());
					}
					
				return null;
			}
		
}