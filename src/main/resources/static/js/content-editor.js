const editorElement = document.querySelector('#content');

if (editorElement) {

    ClassicEditor
        .create(editorElement, {
            extraPlugins: [UploadAdapterPlugin]
        })
        .then(editor => {
            window.editor = editor;
        })
        .catch(error => {
            console.error(error);
        });
}

function UploadAdapterPlugin(editor) {

    editor.plugins
        .get('FileRepository')
        .createUploadAdapter =
        loader => new UploadAdapter(loader);
}