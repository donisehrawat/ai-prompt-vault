import { useState } from 'react'
import ErrorAlert from './ErrorAlert'

export default function PromptCard({ prompt, onEdit, onDelete }) {
    const [deleting, setDeleting] = useState(false)
    const [error, setError] = useState('')

    const handleDelete = async () => {
        if (!confirm('Delete this prompt?')) return
        setDeleting(true)
        setError('')
        try {
            await onDelete(prompt.id || prompt._id)
        } catch (err) {
            setError(err.response?.data?.message || 'Delete failed.')
            setDeleting(false)
        }
    }

    // Attempt to find the prompt id field  
    const promptId = prompt.id || prompt._id

    return (
        <div className="group relative bg-gray-900 rounded-xl border border-gray-800 p-5 shadow-lg hover:border-violet-500/50 hover:shadow-violet-900/20 hover:shadow-xl transition-all duration-300">
            {/* Tag / category pill if available */}
            {prompt.category && (
                <span className="inline-block mb-3 px-2 py-0.5 rounded-full text-xs font-medium bg-violet-500/10 border border-violet-500/20 text-violet-400">
                    {prompt.category}
                </span>
            )}

            {/* Title */}
            <h3 className="text-base font-semibold text-white mb-2 line-clamp-1">
                {prompt.title || 'Untitled Prompt'}
            </h3>

            {/* Content */}
            <p className="text-sm text-gray-400 line-clamp-3 leading-relaxed mb-4">
                {prompt.content || prompt.prompt || prompt.text || '—'}
            </p>

            {error && <ErrorAlert message={error} onDismiss={() => setError('')} />}

            {/* Actions */}
            <div className="flex items-center gap-2 pt-3 border-t border-gray-800">
                <button
                    onClick={() => onEdit(prompt)}
                    className="flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-xs font-medium text-gray-400 hover:text-white hover:bg-gray-800 border border-transparent hover:border-gray-700 transition-all duration-200"
                >
                    <svg className="w-3.5 h-3.5" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                        <path strokeLinecap="round" strokeLinejoin="round" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
                    </svg>
                    Edit
                </button>
                <button
                    onClick={handleDelete}
                    disabled={deleting}
                    className="flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-xs font-medium text-red-400/70 hover:text-red-400 hover:bg-red-500/10 border border-transparent hover:border-red-500/20 disabled:opacity-50 transition-all duration-200"
                >
                    <svg className="w-3.5 h-3.5" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                        <path strokeLinecap="round" strokeLinejoin="round" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                    </svg>
                    {deleting ? 'Deleting…' : 'Delete'}
                </button>
            </div>
        </div>
    )
}
