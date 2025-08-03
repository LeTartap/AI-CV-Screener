from fastapi import UploadFile
import pdfplumber
import docx
import tempfile
import shutil
import os

async def extract_text(file: UploadFile) -> str:
    # Create a temporary file to work with
    suffix = os.path.splitext(file.filename)[1]
    with tempfile.NamedTemporaryFile(delete=False, suffix=suffix) as tmp:
        # Copy the uploaded file to temp file
        await file.seek(0)
        shutil.copyfileobj(file.file, tmp)
        temp_path = tmp.name

    text = ""

    if temp_path.lower().endswith(".pdf"):
        with pdfplumber.open(temp_path) as pdf:
            for page in pdf.pages:
                page_text = page.extract_text()
                if page_text:
                    text += page_text + "\n"

    elif temp_path.lower().endswith((".docx", ".doc")):
        doc = docx.Document(temp_path)
        for para in doc.paragraphs:
            text += para.text + "\n"

    else:
        text = "Unsupported file type."

    # Clean up temp file
    os.remove(temp_path)
    return text.strip()
