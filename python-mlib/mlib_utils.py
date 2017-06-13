
from flask import Flask, render_template, request, flash, jsonify
import os
import nltk
from nltk.corpus import stopwords
import re
import string
from nltk.stem.wordnet import WordNetLemmatizer
from nltk.tokenize import word_tokenize
from nltk.corpus import wordnet as wn
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.naive_bayes import MultinomialNB
import warnings
import numpy as np
import numpy.linalg as LA
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfTransformer
import json
warnings.filterwarnings("ignore")

app = Flask(__name__)
# Defining a SECRET Key
app.config.from_object(__name__)


accomplishmentsCorpus = open("corpusUpdated/accomplishments.txt","r")
awardsCorpus = open("corpusUpdated/awards.txt","r")
credibilityCorpus = open("corpusUpdated/credibility.txt","r")
educationCorpus = open("corpusUpdated/Education.txt","r")
experienceCorpus = open("corpusUpdated/experience.txt","r")
extracurricularCorpus = open("corpusUpdated/extracurricular.txt","r")
miscellaneousCorpus = open("corpusUpdated/miscellaneous.txt","r")
skillsCorpus = open("corpusUpdated/skills.txt","r")
summaryCorpus = open("corpusUpdated/summary.txt","r")
ProfessionCorpus = open("corpusUpdated/profession.txt","r")
OthersCorpus = open("corpusUpdated/others.txt","r")

def makeCorpusList(corpusFile):
    fileList = corpusFile.readlines()
    fileList = [words.lower().strip() for words in fileList]
    fileList = list(set(fileList))
    return fileList

accomplishmentList = makeCorpusList(accomplishmentsCorpus)
awardsList = makeCorpusList(awardsCorpus)
credibilityList = makeCorpusList(credibilityCorpus)
educationList = makeCorpusList(educationCorpus)
experienceList = makeCorpusList(experienceCorpus)
extracurricularList = makeCorpusList(experienceCorpus)
miscellaneousList = makeCorpusList(miscellaneousCorpus)
skillsList = makeCorpusList(skillsCorpus)
summaryList = makeCorpusList(skillsCorpus)
professionList = makeCorpusList(ProfessionCorpus)
othersList = makeCorpusList(OthersCorpus)



def is_noun(tag):
    return tag in ['NN', 'NNS', 'NNP', 'NNPS']

def is_verb(tag):
    return tag in ['VB', 'VBD', 'VBG', 'VBN', 'VBP', 'VBZ']

def is_adverb(tag):
    return tag in ['RB', 'RBR', 'RBS']

def is_adjective(tag):
    return tag in ['JJ', 'JJR', 'JJS']

def penn_to_wn(tag):
    if is_adjective(tag):
        return wn.ADJ
    elif is_noun(tag):
        return wn.NOUN
    elif is_adverb(tag):
        return wn.ADV
    elif is_verb(tag):
        return wn.VERB
    return wn.NOUN

FacultyInfoTrainingData = pd.read_csv("FacultyInfo.csv")
ResearchAreasTrainingData = pd.read_csv("ResearchAreasText.csv")
vectorizer = TfidfVectorizer(min_df=5,
                             max_df=0.8,
                             sublinear_tf=True,
                             use_idf=True,
                             ngram_range=(1, 3))




# Initializing the route of the page with the app
@app.route('/', methods=['POST'])
def getResearchFields():
    jsondata=request.get_json()
    newText =jsondata["text"]
    email = re.findall(r'[\w\.-]+@[\w\.-]+', newText)
    myText = newText.lower()
    wordCount = len(myText.split())
    Score = 0
    if (len(email) > 0):
        Score = Score + 2
    if (any(word in myText for word in accomplishmentList)):
        Score = Score + 1
    if (any(word in myText for word in awardsList)):
        Score = Score + 1
    if (any(word in myText for word in credibilityList)):
        Score = Score + 1
    if (any(word in myText for word in educationList)):
        Score = Score + 1
    if (any(word in myText for word in experienceList)):
        Score = Score + 1
    if (any(word in myText for word in extracurricularList)):
        Score = Score + 1
    if (any(word in myText for word in miscellaneousList)):
        Score = Score + 1
    if (any(word in myText for word in skillsList)):
        Score = Score + 1
    if (any(word in myText for word in summaryList)):
        Score = Score + 1
    if (any(word in myText for word in professionList)):
        Score = Score + 1
    if (any(word in myText for word in othersList)):
        Score = Score + 1
    if ((wordCount > 100) & (Score >= 7)):
        isFaculty = "YES"
    else:
        isFaculty = "NO"

    myText = re.sub(r"http\S+", "", myText)
    myText = ''.join(myText).replace("\n", " "). \
        replace("'re", " are"). \
        replace("'s", " is"). \
        replace("n't", " not"). \
        replace("'d", " had"). \
        replace("'ll", " will"). \
        replace("'m", " am"). \
        replace("'ve", " have")
    myText = myText.translate(str.maketrans('', '', string.punctuation))
    stop = set(stopwords.words('english'))
    myText = [i for i in myText.lower().split() if i not in stop]
    myText = set(myText)
    myText = list(myText)
    myText = ' '.join(myText)
    tags = nltk.pos_tag(word_tokenize(myText))
    myWords = []
    for tag in tags:
        wn_tag = penn_to_wn(tag[1])
        myWords.append(WordNetLemmatizer().lemmatize(tag[0], wn_tag))
    myText = ' '.join(myWords)


    FacultyTrainVectors = vectorizer.fit_transform(FacultyInfoTrainingData.text)
    FacultyPredictionModel = MultinomialNB(fit_prior=False)
    FacultyPredictionModel.fit(FacultyTrainVectors, FacultyInfoTrainingData.field)
    facultyPrediction = FacultyPredictionModel.predict(vectorizer.transform([myText]))


    ResearchAreaTrainVectors = vectorizer.fit_transform(ResearchAreasTrainingData.text)
    ResearchAreasModel = MultinomialNB(fit_prior=False)
    ResearchAreasModel.fit(ResearchAreaTrainVectors, ResearchAreasTrainingData.ResearchArea)

    json_dict = {}
    if (isFaculty == "YES") & (facultyPrediction == "Computer Science"):
        try:
            ResearchAreasProbabilities = ResearchAreasModel.predict_proba(vectorizer.transform([myText]))
            ResearchAreasProbabilities = ResearchAreasProbabilities.flatten()
            ResearchAreas = ResearchAreasModel.classes_
            topResearchAreas = pd.DataFrame()
            topResearchAreas["Area"] = ResearchAreas
            topResearchAreas["probability"] = ResearchAreasProbabilities

            topResearchAreas = topResearchAreas.sort(columns=['probability'], ascending=False)

            topResearchAreas = topResearchAreas.head(3)
            topResearchAreas = topResearchAreas["Area"].values.tolist()
        except:
            topResearchAreas = "null"
        if True:
            try:
                email = email[0]
            except IndexError:
                email = ""    

        json_dict["isProperDocument"] = True
        json_dict["topResearchAreas"] = topResearchAreas
        json_dict["email"] = email

    else:
        json_dict["isProperDocument"] = False


    return jsonify(json_dict)

@app.route('/query', methods=['POST'])
def getWordsFromText():
    jsondata=request.get_json()
    query =jsondata["text"]
    myWords = query
    myWords = myWords.lower()
    myWords = re.sub(r"http\S+", "", myWords)
    myWords = ''.join(myWords).replace("\n", " "). \
        replace("'re", " are"). \
        replace("'s", " is"). \
        replace("n't", " not"). \
        replace("'d", " had"). \
        replace("'ll", " will"). \
        replace("'m", " am"). \
        replace("'ve", " have")
    myWords = myWords.translate(str.maketrans('', '', string.punctuation))
    stop = set(stopwords.words('english'))
    myWords = [i for i in myWords.lower().split() if i not in stop]
    myWords = set(myWords)
    myWords = list(myWords)
    myWords = ' '.join(myWords)
    tags = nltk.pos_tag(word_tokenize(myWords))
    allWords = []
    wordsJson = {}
    for tag in tags:
        wn_tag = penn_to_wn(tag[1])
        allWords.append(WordNetLemmatizer().lemmatize(tag[0], wn_tag))
        wordsJson["words"] = allWords
    return jsonify(wordsJson)



def GetDocumentandRank(document, directQueryWords, expandedQueryWords,wordDict):
    DocumentWords = document.split()
    DocumentDirectQueryIntersection = list(set(directQueryWords).intersection(DocumentWords))
    DocumentExpandedQueryIntersection = list(set(expandedQueryWords).intersection(DocumentWords))
    DirectQueryWordsScoreDict = dict.fromkeys(DocumentDirectQueryIntersection, 1)
    ExpandedQueryWordsScoreDict = dict.fromkeys(DocumentExpandedQueryIntersection, 0.5)
    WordsInDocumentFoundInDirectQueryDict = {k: wordDict[k] for k, v in DirectQueryWordsScoreDict.items() if
                                             k in wordDict}
    WordsInDocumentFoundInExpandedQueryDict = {k: wordDict[k] for k, v in ExpandedQueryWordsScoreDict.items() if
                                               k in wordDict}
    WordsInDocumentFoundInDirectQueryDict.update(WordsInDocumentFoundInExpandedQueryDict)
    DirectQueryWordsScoreDict.update(ExpandedQueryWordsScoreDict)
    AllQueryWordsScoreDict = DirectQueryWordsScoreDict
    FinalScore = sum({k: v * wordDict[k] for k, v in AllQueryWordsScoreDict.items() if k in wordDict}.values())

    return FinalScore

@app.route('/ranking', methods=['POST'])
def ranking():
    if request.method == 'POST':
        DataAfterQuery = request.get_json()
        updatedJson = updateWithDocumentScores(DataAfterQuery)
        return jsonify(updatedJson)


def updateWithDocumentScores(DataAfterQuery):
    DocumentsURLList = list(DataAfterQuery['ExtractedDocuments'].keys())

    tf = TfidfVectorizer(analyzer='word', min_df = 0, stop_words = 'english', use_idf= True)

    AllDocuments = " ".join(list(DataAfterQuery['ExtractedDocuments'].values()))

    tfidf_matrix =  tf.fit_transform([AllDocuments])

    feature_names = tf.get_feature_names()

    dense = tfidf_matrix.todense()

    document = dense[0].tolist()[0]

    phrase_scores = [pair for pair in zip(range(0, len(document)), document) if pair[1] > 0]

    sorted_phrase_scores = sorted(phrase_scores, key=lambda t: t[1] * -1)

    wordDict = {}

    for phrase, score in [(feature_names[word_id], score) for (word_id, score) in sorted_phrase_scores]:
       wordDict.update({phrase:score})


    DirectQueryWords = DataAfterQuery['QueryWords']
    ExpandedQueryWords = DataAfterQuery["ExpandedQueryWords"]


    ScoreDict = {}
    for url in DocumentsURLList:
        DocScore = GetDocumentandRank(DataAfterQuery["ExtractedDocuments"][url],
                                      directQueryWords = DirectQueryWords,
                                      expandedQueryWords = ExpandedQueryWords,
                                      wordDict = wordDict)
        ScoreDict.update({url:DocScore})
    DataAfterQuery['URLscores']= ScoreDict
    return DataAfterQuery

ResearchAreasData = json.loads(open("data_with_fomatting.json").read())

@app.route('/description', methods=['POST'])
def getDescriptionScores():
    ValuesList = []
    for data in range(len(ResearchAreasData)):
        ValuesData = list(ResearchAreasData[data].values())
        FlattenValues = [item for sublist in ValuesData for item in sublist]
        FlattenValues = " ".join(FlattenValues)
        ValuesList.append(FlattenValues)

    jsondata=request.get_json()
    Query =jsondata["text"]
    train_set = ValuesList #Documents
    test_set = [Query] #Query
    stopWords = stopwords.words('english')

    countvectorizer = CountVectorizer(stop_words = stopWords)


    trainVectorizerArray = countvectorizer.fit_transform(train_set).toarray()
    testVectorizerArray = countvectorizer.transform(test_set).toarray()

    cx = lambda a, b : round(np.inner(a, b)/(LA.norm(a)*LA.norm(b)), 3)


    ScoreValues = []
    for vector in trainVectorizerArray:
        for testV in testVectorizerArray:
            cosine = cx(vector, testV)
            ScoreValues.append(cosine)

    ScoreValues = [ '%.3f' % elem for elem in ScoreValues ]

    ResearchAreaList = []
    for elements in range(len(ResearchAreasData)):
        ResearchString = list(ResearchAreasData[elements].keys())[0]
        ResearchAreaList.append(ResearchString)

    ScoreDictionary = dict(zip(ResearchAreaList, ScoreValues))
    return jsonify(ScoreDictionary)	
if __name__ == "__main__":  # Parsing your file
    app.run(host='0.0.0.0', port=5000, debug=True)  # Defining the host and port for your file.
