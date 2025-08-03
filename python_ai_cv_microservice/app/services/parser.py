from fastapi import UploadFile

async def extract_text(file: UploadFile) -> str:
    # For now, just simulate extracting text from the CV
    contents = await file.read()
    # In real implementation: use pdfplumber or python-docx
    return f"Dummy CV text extracted from {file.filename}.\nCandidate has experience with Java, Spring Boot, SQL."
